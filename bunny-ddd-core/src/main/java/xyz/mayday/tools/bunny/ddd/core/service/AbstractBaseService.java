package xyz.mayday.tools.bunny.ddd.core.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import xyz.mayday.tools.bunny.ddd.core.constant.GenericTypeIndexConstant;
import xyz.mayday.tools.bunny.ddd.core.domain.AbstractBaseDTO;
import xyz.mayday.tools.bunny.ddd.core.query.QuerySpecification;
import xyz.mayday.tools.bunny.ddd.core.query.visit.BaseQuerySpecVisitor;
import xyz.mayday.tools.bunny.ddd.core.query.visit.ExtraCriteriaVisitorImpl;
import xyz.mayday.tools.bunny.ddd.core.query.visit.FieldCriteriaVisitorImpl;
import xyz.mayday.tools.bunny.ddd.core.query.visit.MultipleValueCriteriaVisitorImpl;
import xyz.mayday.tools.bunny.ddd.schema.auth.PrincipalService;
import xyz.mayday.tools.bunny.ddd.schema.converter.GenericConverter;
import xyz.mayday.tools.bunny.ddd.schema.domain.BaseDAO;
import xyz.mayday.tools.bunny.ddd.schema.page.PageableData;
import xyz.mayday.tools.bunny.ddd.schema.query.CommonQueryParam;
import xyz.mayday.tools.bunny.ddd.schema.service.BaseService;
import xyz.mayday.tools.bunny.ddd.schema.service.IdGenerator;
import xyz.mayday.tools.bunny.ddd.utils.ReflectionUtils;

import com.google.common.collect.Lists;

@NoArgsConstructor
public abstract class AbstractBaseService<ID extends Serializable, DTO extends AbstractBaseDTO<ID>, DAO extends BaseDAO<ID>> implements BaseService<ID, DTO> {
    
    @Autowired(required = false)
    @Setter
    protected GenericConverter converter;
    
    @Setter
    @Autowired(required = false)
    protected PrincipalService principalService;
    
    @Autowired(required = false)
    @Setter
    protected IdGenerator<String> idGenerator;
    
    public AbstractBaseService(GenericConverter converter, PrincipalService principalService, IdGenerator<String> idGenerator) {
        this.converter = converter;
        this.principalService = principalService;
        this.idGenerator = idGenerator;
    }
    
    @Override
    public final PageableData<DTO> findItems(DTO example, CommonQueryParam queryParam) {
        return doFindItems(example, queryParam);
    }
    
    protected abstract PageableData<DTO> doFindItems(DTO example, CommonQueryParam queryParam);
    
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
    public final Class<DTO> getDomainClass() {
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
        dao.setVersion(1);
        dao.setCreatedBy(principalService.getCurrentUserId());
        dao.setUpdatedBy(principalService.getCurrentUserId());
        dao.setCreatedDate(now);
        dao.setUpdatedDate(now);
    }
    
    protected void auditWhenUpdate(DAO dao) {
        Date now = new Date();
        dao.setUpdatedBy(principalService.getCurrentUserId());
        dao.setUpdatedDate(now);
    }
    
    protected ID convertIdType(String id) {
        return converter.convert(id, getIdType());
    }
    
    protected void mergeProperties(DTO source, DTO target) {
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
