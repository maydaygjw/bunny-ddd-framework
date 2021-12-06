package xyz.mayday.tools.bunny.ddd.cache.service;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import xyz.mayday.tools.bunny.ddd.core.domain.AbstractBaseDTO;
import xyz.mayday.tools.bunny.ddd.core.service.AbstractBaseService;
import xyz.mayday.tools.bunny.ddd.schema.auth.PrincipalService;
import xyz.mayday.tools.bunny.ddd.schema.cache.CacheQueryField;
import xyz.mayday.tools.bunny.ddd.schema.converter.GenericConverter;
import xyz.mayday.tools.bunny.ddd.schema.domain.BaseDAO;
import xyz.mayday.tools.bunny.ddd.schema.page.PageableData;
import xyz.mayday.tools.bunny.ddd.schema.query.CommonQueryParam;
import xyz.mayday.tools.bunny.ddd.schema.service.BaseService;
import xyz.mayday.tools.bunny.ddd.schema.service.CacheableService;
import xyz.mayday.tools.bunny.ddd.schema.service.HistoryService;
import xyz.mayday.tools.bunny.ddd.schema.service.IdGenerator;
import xyz.mayday.tools.bunny.ddd.utils.ReflectionUtils;

@Slf4j
public abstract class CacheableServiceImpl<ID extends Serializable, DTO extends AbstractBaseDTO<ID>, DAO extends BaseDAO<ID>>
        extends AbstractBaseService<ID, DTO, DAO> implements CacheableService<ID, DTO> {
    
    final AbstractBaseService<ID, DTO, DAO> underlyingService;
    
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    
    public CacheableServiceImpl(AbstractBaseService<ID, DTO, DAO> underlyingService) {
        this.underlyingService = underlyingService;
    }
    
    public CacheableServiceImpl(GenericConverter converter, PrincipalService principalService, IdGenerator<String> idGenerator, HistoryService historyService,
            AbstractBaseService<ID, DTO, DAO> underlyingService) {
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
    public PageableData<DTO> doFindItems(DTO example, CommonQueryParam queryParam) {
        return underlyingService.doFindItems(example, queryParam);
    }
    
    @Override
    public Long countItems(DTO example) {
        return null;
    }
    
    @Override
    public List<DTO> findAll(DTO example) {
        if (hitCache(example)) {
            return redisTemplate.<String, DAO> opsForHash().entries(getCacheEntityName()).values().stream().map(this::convertToDto)
                    .collect(Collectors.toList());
        } else {
            return underlyingService.findAll(example);
        }
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
    
    protected Boolean hitCache(DTO example) {
        
        return true;
    }
    
    @Override
    public void initCacheData() {
        
        List<DAO> all = underlyingService.findAll(null).stream().map(this::convertToDao).collect(Collectors.toList());
        
        redisTemplate.opsForHash().putAll("main_" + getCacheEntityName(), all.stream().collect(Collectors.groupingBy(DAO::getId)));
        
        List<Field> fieldsListWithAnnotation = FieldUtils.getFieldsListWithAnnotation(getDaoClass(), CacheQueryField.class);
        fieldsListWithAnnotation.forEach(field -> {
            Map<Object, List<DAO>> collect = all.stream().collect(Collectors.groupingBy(dao -> ReflectionUtils.getValue(field, dao)));
            String indexCacheName = "idx_" + getCacheEntityName() + "_" + field.getName();
            redisTemplate.opsForHash().putAll(indexCacheName, collect);
        });
        
    }
    
    @Override
    public String getCacheEntityName() {
        return getDaoClass().getCanonicalName();
    }
    
    @Override
    public Integer getDaysOfCachedData() {
        return 10;
    }
    
}
