package xyz.mayday.tools.bunny.ddd.core.service;

import xyz.mayday.tools.bunny.ddd.schema.service.BaseService;

public abstract class AbstractBaseService<ID, DTO, DAO> implements BaseService<ID, DTO> {

    public final Class<DTO> getDtoClass() {
        return null;
    }

    public final Class<DTO> getDaoClass() {
        return null;
    }

    @Override
    public final Class<DTO> getDomainClass() {
        return getDtoClass();
    }

    protected DTO convertToDto(DAO dao) {
        return null;
    }

    protected DAO convertToDao(DTO dto) {
        return null;
    }

}
