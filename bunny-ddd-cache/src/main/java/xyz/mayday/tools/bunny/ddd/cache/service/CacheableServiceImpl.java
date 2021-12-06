package xyz.mayday.tools.bunny.ddd.cache.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import xyz.mayday.tools.bunny.ddd.core.domain.AbstractBaseDTO;
import xyz.mayday.tools.bunny.ddd.core.service.AbstractBaseService;
import xyz.mayday.tools.bunny.ddd.schema.auth.PrincipalService;
import xyz.mayday.tools.bunny.ddd.schema.converter.GenericConverter;
import xyz.mayday.tools.bunny.ddd.schema.domain.BaseDAO;
import xyz.mayday.tools.bunny.ddd.schema.page.PageableData;
import xyz.mayday.tools.bunny.ddd.schema.query.CommonQueryParam;
import xyz.mayday.tools.bunny.ddd.schema.service.BaseService;
import xyz.mayday.tools.bunny.ddd.schema.service.CacheableService;
import xyz.mayday.tools.bunny.ddd.schema.service.HistoryService;
import xyz.mayday.tools.bunny.ddd.schema.service.IdGenerator;

public class CacheableServiceImpl<ID extends Serializable, DTO extends AbstractBaseDTO<ID>, DAO extends BaseDAO<ID>> extends AbstractBaseService<ID, DTO, DAO>
        implements CacheableService<ID, DTO> {
    
    final BaseService<ID, DTO> underlyingService;
    
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    
    public CacheableServiceImpl(BaseService<ID, DTO> underlyingService) {
        this.underlyingService = underlyingService;
    }
    
    public CacheableServiceImpl(GenericConverter converter, PrincipalService principalService, IdGenerator<String> idGenerator, HistoryService historyService,
            BaseService<ID, DTO> underlyingService) {
        super(converter, principalService, idGenerator, historyService);
        this.underlyingService = underlyingService;
    }
    
    @Override
    public Optional<DTO> findItemById(ID id) {
        return Optional.ofNullable(redisTemplate.<String, DAO> opsForHash().get(getCacheEntityName(), id)).map(this::convertToDto);
    }
    
    @Override
    public List<DTO> findItemsByIds(List<ID> id) {
        return null;
    }
    
    @Override
    public List<DTO> findHistoriesById(ID id) {
        return underlyingService.findHistoriesById(id);
    }
    
    @Override
    protected PageableData<DTO> doFindItems(DTO example, CommonQueryParam queryParam) {
        return null;
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
        DTO insert = underlyingService.insert(dto);
        redisTemplate.opsForHash().put(getCacheEntityName(), insert.getId(), convertToDao(insert));
        return insert;
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
    public BaseService<ID, DTO> getUnderlyingService() {
        return underlyingService;
    }
    
    @Override
    public void createCache() {
    }
    
    @Override
    public void destroyCache() {
        
    }
    
    @Override
    public void initCacheData() {
        
    }
    
    @Override
    public String getCacheEntityName() {
        return getDaoClass().getCanonicalName();
    }
}
