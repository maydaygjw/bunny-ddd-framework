package xyz.mayday.tools.bunny.ddd.qdsl.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;

import xyz.mayday.tools.bunny.ddd.qdsl.domain.BaseQueryableDTO;
import xyz.mayday.tools.bunny.ddd.schema.page.PageableData;
import xyz.mayday.tools.bunny.ddd.schema.query.CommonQueryParam;
import xyz.mayday.tools.bunny.ddd.schema.service.BaseService;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class DefaultDSLQueryableService<ID extends Serializable, DTO extends BaseQueryableDTO<ID>> implements BaseDSLQueryableService<ID, DTO> {
    
    @Autowired
    JPAQueryFactory jpaQueryFactory;
    
    BaseService<ID, DTO> baseService;
    
    public DefaultDSLQueryableService(BaseService<ID, DTO> baseService) {
        this.baseService = baseService;
    }
    
    protected BaseService<ID, DTO> getService() {
        return baseService;
    }
    
    @Override
    public Optional<DTO> findItemById(ID id) {
        return getService().findItemById(id);
    }
    
    @Override
    public List<DTO> findItemsByIds(List<ID> id) {
        return getService().findItemsByIds(id);
    }
    
    @Override
    public List<DTO> findHistoriesById(ID id) {
        return getService().findHistoriesById(id);
    }
    
    @Override
    public PageableData<DTO> findItems(DTO example, CommonQueryParam queryParam) {
        return getService().findItems(example, queryParam);
    }
    
    @Override
    public Long countItems(DTO example) {
        return null;
    }
    
    @Override
    public List<DTO> findAll(DTO example) {
        return null;
    }
    
    @Override
    public Stream<DTO> findStream(DTO example) {
        return null;
    }
    
    @Override
    public DTO insert(DTO dto) {
        return null;
    }
    
    @Override
    public List<DTO> bulkInsert(List<DTO> dtos) {
        return null;
    }
    
    @Override
    public DTO update(DTO dto) {
        return null;
    }
    
    @Override
    public List<DTO> bulkUpdate(List<DTO> dtos) {
        return null;
    }
    
    @Override
    public DTO save(DTO dto) {
        return null;
    }
    
    @Override
    public DTO delete(ID id) {
        return null;
    }
    
    @Override
    public List<DTO> bulkDeleteById(List<ID> ids) {
        return null;
    }
    
    @Override
    public List<DTO> deleteAll() {
        return null;
    }
    
    @Override
    public Class<DTO> getDomainClass() {
        return getService().getDomainClass();
    }
    
    @Override
    public JPAQueryFactory getJpaQueryFactory() {
        return jpaQueryFactory;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public JPAQuery<DTO> selectFrom(DTO dQuery) {
        return (JPAQuery<DTO>) jpaQueryFactory.selectFrom(dQuery.getQuery());
    }
}
