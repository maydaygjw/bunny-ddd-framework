package xyz.mayday.tools.bunny.ddd.core.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;
import xyz.mayday.tools.bunny.ddd.core.constant.GenericTypeIndexConstant;
import xyz.mayday.tools.bunny.ddd.schema.controller.BaseController;
import xyz.mayday.tools.bunny.ddd.schema.converter.GenericConverter;
import xyz.mayday.tools.bunny.ddd.schema.domain.BaseVO;
import xyz.mayday.tools.bunny.ddd.schema.page.PageableData;
import xyz.mayday.tools.bunny.ddd.schema.page.PagingParameters;
import xyz.mayday.tools.bunny.ddd.schema.query.CommonQueryParam;
import xyz.mayday.tools.bunny.ddd.utils.ReflectionUtils;

/** @author gejunwen */
@RequiredArgsConstructor
public abstract class BaseControllerImpl<ID, VO extends BaseVO<ID>, QUERY, DTO>
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
    commonQueryParam = applyQueryRestriction(commonQueryParam);
    PageableData<DTO> items = getService().findItems(convertQueryToDto(query), commonQueryParam);
    return PageableData.<VO>builder()
        .records(items.getRecords().stream().map(this::convertDtoToVo).collect(Collectors.toList()))
        .pageInfo(items.getPageInfo())
        .build();
  }

  @Override
  public Long countItems(QUERY query) {
    return getService().countItems(convertQueryToDto(query));
  }

  CommonQueryParam applyQueryRestriction(CommonQueryParam commonQueryParam) {

    commonQueryParam = Optional.ofNullable(commonQueryParam).orElse(new CommonQueryParam());

    if (CollectionUtils.isEmpty(commonQueryParam.getSortField())) {
      commonQueryParam.setSortField(Collections.singletonList("updatedDate"));
      commonQueryParam.setSortOrder(Collections.singletonList(Sort.Direction.DESC.name()));
    }

    if (Objects.isNull(commonQueryParam.getPageSize()))
      commonQueryParam.setPageSize(pagingConfigure.getDefaultPageSize());
    if (Objects.isNull(commonQueryParam.getCurrentPage())) commonQueryParam.setCurrentPage(1);
    if (commonQueryParam.getPageSize() > pagingConfigure.getPageSizeLimit())
      commonQueryParam.setPageSize(pagingConfigure.getPageSizeLimit());

    return commonQueryParam;
  }

  @Override
  public List<VO> bulkDelete(List<ID> ids) {
    return getService().bulkDeleteById(ids).stream()
        .map(this::convertDtoToVo)
        .collect(Collectors.toList());
  }

  @ApiOperationSupport(ignoreParameters = {"id", "revision", "version", "operationType"})
  @Override
  public VO create(VO vo) {
    return convertDtoToVo(getService().insert(convertVoToDto(vo)));
  }

  @ApiOperationSupport(ignoreParameters = {"revision", "version", "operationType"})
  @Override
  public VO update(VO vo) {
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
  public List<VO> queryAll(CommonQueryParam commonQueryParam) {
    return null;
  }

  @Override
  public List<VO> findHistories(ID id) {
    return getService().findHistoriesById(id).stream()
        .map(this::convertDtoToVo)
        .collect(Collectors.toList());
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
    return ReflectionUtils.getGenericTypeOfSuperClass(
        this, GenericTypeIndexConstant.ControllerTypeIndex.IDX_VO);
  }

  protected Class<DTO> getDTOClass() {
    return ReflectionUtils.getGenericTypeOfSuperClass(
        this, GenericTypeIndexConstant.ControllerTypeIndex.IDX_DTO);
  }
}
