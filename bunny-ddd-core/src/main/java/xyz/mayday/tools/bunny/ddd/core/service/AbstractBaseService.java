package xyz.mayday.tools.bunny.ddd.core.service;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import xyz.mayday.tools.bunny.ddd.schema.auth.PrincipalService;
import xyz.mayday.tools.bunny.ddd.schema.converter.GenericConverter;
import xyz.mayday.tools.bunny.ddd.schema.domain.BaseDAO;
import xyz.mayday.tools.bunny.ddd.schema.service.BaseService;
import xyz.mayday.tools.bunny.ddd.utils.ReflectionUtils;

import javax.inject.Inject;
import java.util.Date;

@NoArgsConstructor
public abstract class AbstractBaseService<ID, DTO, DAO extends BaseDAO<ID>> implements BaseService<ID, DTO> {

    @Autowired(required = false)
    GenericConverter converter;

    @Autowired(required = false)
    PrincipalService principalService;

    public AbstractBaseService(GenericConverter converter, PrincipalService principalService) {
        this.converter = converter;
        this.principalService = principalService;
    }

    public final Class<ID> getIdType() {
        return ReflectionUtils.getGenericTypeOfSuperClass(this, 0);
    }

    public final Class<DTO> getDtoClass() {
        return ReflectionUtils.getGenericTypeOfSuperClass(this, 1);
    }

    public final Class<DAO> getDaoClass() {
        return ReflectionUtils.getGenericTypeOfSuperClass(this, 2);
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
        dao.setCreatedBy(principalService.getCurrentUserId());
        dao.setUpdatedBy(principalService.getCurrentUserId());
        dao.setCreatedDate(now);
        dao.setUpdatedDate(now);
    }

    protected void auditWhenUpdated(DAO dao) {
        Date now = new Date();
        dao.setUpdatedBy(principalService.getCurrentUserId());
        dao.setUpdatedDate(now);
    }

    protected ID convertIdType(String id) {
        return converter.convert(id, getIdType());
    }

}
