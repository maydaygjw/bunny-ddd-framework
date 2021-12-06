package xyz.mayday.tools.bunny.ddd.core.controller;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import xyz.mayday.tools.bunny.ddd.core.constant.GenericTypeIndexConstant;
import xyz.mayday.tools.bunny.ddd.core.domain.AbstractBaseDTO;
import xyz.mayday.tools.bunny.ddd.schema.controller.BaseController;
import xyz.mayday.tools.bunny.ddd.schema.converter.GenericConverter;
import xyz.mayday.tools.bunny.ddd.schema.domain.BaseVO;
import xyz.mayday.tools.bunny.ddd.schema.page.PageableData;
import xyz.mayday.tools.bunny.ddd.schema.page.PagingParameters;
import xyz.mayday.tools.bunny.ddd.schema.query.CommonQueryParam;
import xyz.mayday.tools.bunny.ddd.schema.query.annotation.QueryComparator;
import xyz.mayday.tools.bunny.ddd.utils.ReflectionUtils;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;

/** @author gejunwen */
@RequiredArgsConstructor
public abstract class BaseControllerImpl<ID extends Serializable, VO extends BaseVO<ID>, QUERY, DTO extends AbstractBaseDTO<ID>>
        implements BaseController<ID, VO, QUERY, DTO> {
    
    @Autowired(required = false)
    GenericConverter converter;
    
    @Autowired(required = false)
    PagingParameters pagingConfigure;
    
    public BaseControllerImpl(GenericConverter converter, PagingParameters pagingConfigure) {
        this.converter = converter;
        this.pagingConfigure = pagingConfigure;
    }
    
    @Override
    public PageableData<VO> queryItems(QUERY query, CommonQueryParam commonQueryParam) {
        DTO dto = applyQuery(query, commonQueryParam);
        PageableData<DTO> items = getService().findItems(dto, commonQueryParam);
        return PageableData.<VO> builder().records(items.getRecords().stream().map(this::convertDtoToVo).collect(Collectors.toList()))
                .pageInfo(items.getPageInfo()).build();
    }
    
    @Override
    public Long countItems(QUERY query) {
        return getService().countItems(convertQueryToDto(query));
    }
    
    DTO applyQuery(QUERY query, CommonQueryParam commonQueryParam) {
        
        commonQueryParam = Optional.ofNullable(commonQueryParam).orElse(new CommonQueryParam());
        
        if (CollectionUtils.isEmpty(commonQueryParam.getSortField())) {
            commonQueryParam.setSortField(Collections.singletonList("updatedDate"));
            commonQueryParam.setSortOrder(Collections.singletonList(Sort.Direction.DESC.name()));
        }
        
        if (0 >= commonQueryParam.getPageSize())
            commonQueryParam.setPageSize(pagingConfigure.getDefaultPageSize());
        if (Objects.isNull(commonQueryParam.getCurrentPage()))
            commonQueryParam.setCurrentPage(1);
        if (commonQueryParam.getPageSize() > pagingConfigure.getPageSizeLimit())
            commonQueryParam.setPageSize(pagingConfigure.getPageSizeLimit());
        
        DTO dto = convertQueryToDto(query);
        dto.addMultiValues("dataState", commonQueryParam.getDataState());
        
        List<xyz.mayday.tools.bunny.ddd.schema.query.QueryComparator> collect = FieldUtils.getFieldsListWithAnnotation(query.getClass(), QueryComparator.class)
                .stream().map(field -> Pair.of(field, field.getAnnotation(QueryComparator.class))).map(pair -> {
                    QueryComparator comparatorAnnotation = pair.getRight();
                    xyz.mayday.tools.bunny.ddd.schema.query.QueryComparator convert = new xyz.mayday.tools.bunny.ddd.schema.query.QueryComparator()
                            .withSearchOperation(comparatorAnnotation.operation()).withConjunctionGroup(comparatorAnnotation.conjunctionGroup())
                            .withSearchConjunction(comparatorAnnotation.conjunction());
                    Field field = pair.getLeft();
                    convert.setKey(StringUtils.defaultIfBlank(comparatorAnnotation.key(), field.getName()));
                    if (StringUtils.isAllBlank(comparatorAnnotation.compareWith())) {
                        convert.setCompareWith(Collections.singletonList(field.getName()));
                    }
                    convert.setValues(Collections.singleton(ReflectionUtils.getValue(field, query)));
                    return convert;
                }).collect(Collectors.toList());
        dto.addQueryComparators(collect);
        return dto;
        
    }
    
    @Override
    public List<VO> bulkDelete(List<ID> ids) {
        return getService().bulkDeleteById(ids).stream().map(this::convertDtoToVo).collect(Collectors.toList());
    }
    
    @ApiOperationSupport(ignoreParameters = { "id", "revision", "version", "operationType" })
    @Override
    public VO create(VO vo) {
        return convertDtoToVo(getService().insert(convertVoToDto(vo)));
    }
    
    @ApiOperationSupport(ignoreParameters = { "revision", "version", "operationType" })
    @Override
    public VO update(ID id, VO vo) {
        vo.setId(id);
        return convertDtoToVo(getService().update(convertVoToDto(vo)));
    }
    
    @Override
    public VO delete(ID id) {
        return convertDtoToVo(getService().delete(id));
    }
    
    @Override
    public Optional<VO> queryById(ID id) {
        return getService().findItemById(id).map(this::convertDtoToVo);
    }
    
    @Override
    public List<VO> queryAll(QUERY query) {
        DTO dto = applyQuery(query, new CommonQueryParam().withPageSize(pagingConfigure.getPageSizeLimit()));
        return getService().findAll(dto).stream().map(this::convertDtoToVo).collect(Collectors.toList());
    }
    
    @Override
    public List<VO> queryHistories(ID id) {
        return getService().findHistoriesById(id).stream().map(this::convertDtoToVo).collect(Collectors.toList());
    }
    
    public DTO convertQueryToDto(QUERY query) {
        return converter.convert(query, getDTOClass());
    }
    
    public VO convertDtoToVo(DTO dto) {
        return converter.convert(dto, getVOClass());
    }
    
    public DTO convertVoToDto(VO vo) {
        return converter.convert(vo, getDTOClass());
    }
    
    protected Class<VO> getVOClass() {
        return ReflectionUtils.getGenericTypeOfSuperClass(this, GenericTypeIndexConstant.ControllerTypeIndex.IDX_VO);
    }
    
    protected Class<DTO> getDTOClass() {
        return ReflectionUtils.getGenericTypeOfSuperClass(this, GenericTypeIndexConstant.ControllerTypeIndex.IDX_DTO);
    }
}
