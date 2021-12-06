package xyz.mayday.tools.bunny.ddd.reactive.redis;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.inject.Inject;

import lombok.NoArgsConstructor;

import org.springframework.data.redis.core.ReactiveRedisOperations;

import xyz.mayday.tools.bunny.ddd.core.domain.AbstractBaseDTO;
import xyz.mayday.tools.bunny.ddd.core.service.AbstractBaseService;
import xyz.mayday.tools.bunny.ddd.schema.domain.BaseDAO;
import xyz.mayday.tools.bunny.ddd.schema.page.PageableData;
import xyz.mayday.tools.bunny.ddd.schema.query.CommonQueryParam;
import xyz.mayday.tools.bunny.ddd.schema.service.CacheableService;

@NoArgsConstructor
public abstract class RedisCacheableServiceImpl<ID extends Serializable, DTO extends AbstractBaseDTO<ID>, DAO extends BaseDAO<ID>>
        extends AbstractBaseService<ID, DTO, DAO> implements CacheableService<ID, DTO> {
    
    @Inject
    ReactiveRedisOperations<String, DAO> redisOperations;
    
    @Override
    public Optional<DTO> findItemById(ID id) {
        return redisOperations.opsForValue().get(String.valueOf(id)).blockOptional().map(this::convertToDto);
    }
    
    @Override
    public List<DTO> findItemsByIds(List<ID> id) {
        return null;
    }
    
    @Override
    public List<DTO> findHistoriesById(ID id) {
        return null;
    }
    
    @Override
    public PageableData<DTO> doFindItems(DTO example, CommonQueryParam queryParam) {
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
        return null;
    }
    
    @Override
    public DTO update(DTO dto) {
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
    public void createCache() {
    }
    
    @Override
    public void destroyCache() {
    }
    
    @Override
    public void initCacheData() {
    }
}
