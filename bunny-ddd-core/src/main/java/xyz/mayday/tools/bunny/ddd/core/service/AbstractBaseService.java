package xyz.mayday.tools.bunny.ddd.core.service;

import java.util.Date;

import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;

import xyz.mayday.tools.bunny.ddd.core.constant.GenericTypeIndexConstant;
import xyz.mayday.tools.bunny.ddd.schema.auth.PrincipalService;
import xyz.mayday.tools.bunny.ddd.schema.converter.GenericConverter;
import xyz.mayday.tools.bunny.ddd.schema.domain.BaseDAO;
import xyz.mayday.tools.bunny.ddd.schema.service.BaseService;
import xyz.mayday.tools.bunny.ddd.utils.ReflectionUtils;

@NoArgsConstructor
public abstract class AbstractBaseService<ID, DTO, DAO extends BaseDAO<ID>> implements BaseService<ID, DTO> {
    
    @Autowired(required = false)
    @Setter
    GenericConverter converter;
    
    @Setter
    @Autowired(required = false)
    PrincipalService principalService;
    
    public AbstractBaseService(GenericConverter converter, PrincipalService principalService) {
        this.converter = converter;
        this.principalService = principalService;
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
}
