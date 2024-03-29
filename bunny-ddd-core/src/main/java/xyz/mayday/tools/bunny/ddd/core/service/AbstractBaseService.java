package xyz.mayday.tools.bunny.ddd.core.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;

import xyz.mayday.tools.bunny.ddd.core.constant.GenericTypeIndexConstant;
import xyz.mayday.tools.bunny.ddd.core.domain.AbstractBaseDTO;
import xyz.mayday.tools.bunny.ddd.core.query.QuerySpecification;
import xyz.mayday.tools.bunny.ddd.core.query.visit.BaseQuerySpecVisitor;
import xyz.mayday.tools.bunny.ddd.core.query.visit.ExtraCriteriaVisitorImpl;
import xyz.mayday.tools.bunny.ddd.core.query.visit.FieldCriteriaVisitorImpl;
import xyz.mayday.tools.bunny.ddd.core.query.visit.MultipleValueCriteriaVisitorImpl;
import xyz.mayday.tools.bunny.ddd.core.utils.BeanUtils;
import xyz.mayday.tools.bunny.ddd.schema.auth.PrincipalService;
import xyz.mayday.tools.bunny.ddd.schema.converter.GenericConverter;
import xyz.mayday.tools.bunny.ddd.schema.domain.BaseDAO;
import xyz.mayday.tools.bunny.ddd.schema.page.PageableData;
import xyz.mayday.tools.bunny.ddd.schema.query.CommonQueryParam;
import xyz.mayday.tools.bunny.ddd.schema.service.BaseService;
import xyz.mayday.tools.bunny.ddd.schema.service.HistoryService;
import xyz.mayday.tools.bunny.ddd.schema.service.IdGenerator;
import xyz.mayday.tools.bunny.ddd.utils.ReflectionUtils;

import com.google.common.collect.Lists;

@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractBaseService<ID extends Serializable, DTO extends AbstractBaseDTO<ID>, DAO extends BaseDAO<ID>> implements BaseService<ID, DTO> {
    
    @Autowired
    protected GenericConverter converter;
    
    @Autowired
    protected PrincipalService principalService;
    
    @Autowired
    protected IdGenerator<String> idGenerator;
    
    @Autowired
    protected HistoryService historyService;
    
    @Override
    public abstract PageableData<DTO> findItems(DTO example, CommonQueryParam queryParam);
    
    @Override
    public List<DTO> findHistoriesById(ID id) {
        return historyService.findHistoriesById(id, getDaoClass()).stream().map(pair -> {
            DTO dto = convertToDto(pair.getLeft());
            dto.setRevision(pair.getRight().getRevision());
            dto.setChangedProperties(pair.getRight().getChangedProperties());
            dto.setOperationType(pair.getRight().getOperationType());
            dto.setVersion(pair.getRight().getVersion());
            return dto;
        }).collect(Collectors.toList());
    }
    
    public final Class<ID> getIdType() {
        return ReflectionUtils.getGenericTypeOfSuperClass(this, GenericTypeIndexConstant.ServiceTypeIndex.IDX_ID);
    }
    
    public final Class<DTO> getDtoClass() {
        return ReflectionUtils.getGenericTypeOfSuperClass(this, GenericTypeIndexConstant.ServiceTypeIndex.IDX_DTO);
    }
    
    public final Class<DAO> getDaoClass() {
        return ReflectionUtils.getGenericTypeOfSuperClass(this, GenericTypeIndexConstant.ServiceTypeIndex.IDX_DAO);
    }
    
    @Override
    public Class<DTO> getDomainClass() {
        return getDtoClass();
    }
    
    protected DTO convertToDto(DAO dao) {
        return converter.convert(dao, getDtoClass());
    }
    
    protected DAO convertToDao(DTO dto) {
        return converter.convert(dto, getDaoClass());
    }
    
    protected void auditWhenInsert(DAO dao) {
        Date now = new Date();
        dao.setCreatedBy(principalService.getCurrentUserId());
        dao.setUpdatedBy(principalService.getCurrentUserId());
        dao.setCreatedDate(now);
        dao.setUpdatedDate(now);
        dao.setVersion(1);
    }
    
    protected void auditWhenUpdate(DAO dao) {
        Date now = new Date();
        dao.setUpdatedBy(principalService.getCurrentUserId());
        dao.setUpdatedDate(now);
    }
    
    protected ID convertIdType(String id) {
        return converter.convert(id, getIdType());
    }
    
    protected <T> void mergeProperties(T source, T target) {
        BeanUtils.copyProperties(source, target);
    }
    
    protected QuerySpecification<DAO> buildQuerySpecification(DTO dto) {
        QuerySpecification<DAO> querySpecification = new QuerySpecification<>();
        Collection<BaseQuerySpecVisitor> visitors = Lists.newArrayList(new FieldCriteriaVisitorImpl(), new MultipleValueCriteriaVisitorImpl(),
                new ExtraCriteriaVisitorImpl());
        visitors.forEach(dto::accept);
        visitors.forEach(visitor -> querySpecification.addAll(visitor.getSearchCriteria()));
        return querySpecification;
    }
}
