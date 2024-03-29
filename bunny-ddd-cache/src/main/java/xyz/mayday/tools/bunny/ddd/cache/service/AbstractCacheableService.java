package xyz.mayday.tools.bunny.ddd.cache.service;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.core.RedisTemplate;

import xyz.mayday.tools.bunny.ddd.cache.query.CharacterIndexProcessor;
import xyz.mayday.tools.bunny.ddd.cache.query.IndexProcessor;
import xyz.mayday.tools.bunny.ddd.cache.query.RedisUtils;
import xyz.mayday.tools.bunny.ddd.cache.query.SequentialIndexProcessor;
import xyz.mayday.tools.bunny.ddd.core.domain.AbstractBaseDTO;
import xyz.mayday.tools.bunny.ddd.core.service.AbstractBaseService;
import xyz.mayday.tools.bunny.ddd.schema.cache.CacheEntity;
import xyz.mayday.tools.bunny.ddd.schema.cache.CacheQueryField;
import xyz.mayday.tools.bunny.ddd.schema.domain.BaseDAO;
import xyz.mayday.tools.bunny.ddd.schema.exception.BusinessException;
import xyz.mayday.tools.bunny.ddd.schema.page.PageableData;
import xyz.mayday.tools.bunny.ddd.schema.query.CommonQueryParam;
import xyz.mayday.tools.bunny.ddd.schema.query.SearchCriteria;
import xyz.mayday.tools.bunny.ddd.schema.service.BaseService;
import xyz.mayday.tools.bunny.ddd.schema.service.CacheableService;
import xyz.mayday.tools.bunny.ddd.utils.CollectionUtils;
import xyz.mayday.tools.bunny.ddd.utils.ReflectionUtils;
import xyz.mayday.tools.bunny.ddd.utils.convert.SimpleConverter;
import xyz.mayday.tools.bunny.ddd.utils.functional.TriConsumer;

import com.google.common.collect.Sets;

@Slf4j
public abstract class AbstractCacheableService<ID extends Serializable, DTO extends AbstractBaseDTO<ID>, DAO extends BaseDAO<ID>>
        extends AbstractBaseService<ID, DTO, DAO> implements CacheableService<ID, DTO> {
    
    final BaseService<ID, DTO> underlyingService;
    
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    
    @Autowired
    SequentialIndexProcessor sequenceProcessor;
    
    @Autowired
    CharacterIndexProcessor characterProcessor;
    
    public AbstractCacheableService(BaseService<ID, DTO> underlyingService) {
        this.underlyingService = underlyingService;
    }
    
    @Override
    public Optional<DTO> findItemById(ID id) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(RedisUtils.getCacheItemDataKey(id.toString(), getCacheEntityName())))
                .map(dao -> convertToDto(SimpleConverter.convert(dao, getDaoClass())));
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
    public PageableData<DTO> findItems(DTO example, CommonQueryParam queryParam) {
        return underlyingService.findItems(example, queryParam);
    }
    
    @Override
    public Long countItems(DTO example) {
        return null;
    }
    
    @Override
    public List<DTO> findAll(DTO example) {
        List<SearchCriteria> searchCriteriaList = buildQuerySpecification(example).getSearchCriteriaList();
        if (hitCache(searchCriteriaList)) {
            Set<DAO> queryResult = doQueryInCache(searchCriteriaList);
            return queryResult.stream().map(this::convertToDto).collect(Collectors.toList());
        } else {
            return underlyingService.findAll(example);
        }
    }
    
    Set<DAO> doQueryInCache(List<SearchCriteria> criteriaList) {
        Set<DAO> queryResult = new HashSet<>();
        
        for (int i = 0; i < criteriaList.size(); i++) {
            SearchCriteria criteria = criteriaList.get(i);
            Set<String> idList = getIndexProcessor(criteria.getValue()).lookupIds(criteria, getCacheEntityName());
            // find data by id
            Set<DAO> dataList = Objects.requireNonNull(redisTemplate.opsForValue().multiGet(idList)).stream()
                    .map(value -> SimpleConverter.convert(value, getDaoClass())).collect(Collectors.toSet());
            
            log.trace("Query by {}, the result size is: {}", criteria, dataList.size());
            
            if (i == 0) {
                queryResult.addAll(dataList);
            } else {
                queryResult = Sets.intersection(queryResult, dataList);
            }
            
            if (CollectionUtils.isEmpty(queryResult)) {
                break;
            }
        }
        return queryResult;
    }
    
    private IndexProcessor getIndexProcessor(Object value) {
        if (NumberUtils.isCreatable(value.toString()) || TypeUtils.isInstance(value, Date.class)) {
            return sequenceProcessor;
        } else {
            return characterProcessor;
        }
    }
    
    @Override
    public Stream<DTO> findStream(DTO example) {
        return null;
    }
    
    @Override
    public DTO insert(DTO dto) {
        DTO insert = underlyingService.insert(dto);
        DAO dao = convertToDao(insert);
        redisTemplate.opsForValue().set(RedisUtils.getCacheItemDataKey(dao.getId().toString(), getCacheEntityName()), dao);
        buildIndex(Collections.singletonList(dao));
        return insert;
    }
    
    @Override
    public List<DTO> bulkInsert(List<DTO> dtos) {
        return null;
    }
    
    @Override
    public DTO update(DTO dto) {
        DTO update = underlyingService.update(dto);
        DAO dao = convertToDao(update);
        Object result = redisTemplate.opsForValue().getAndSet(RedisUtils.getCacheItemDataKey(dao.getId().toString(), getCacheEntityName()), dao);
        DAO oldOne = SimpleConverter.convert(result, getDaoClass());
        removeIndex(Collections.singletonList(oldOne));
        buildIndex(Collections.singletonList(dao));
        return update;
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
        DTO deleted = underlyingService.delete(id);
        DAO dao = convertToDao(deleted);
        redisTemplate.delete(RedisUtils.getCacheItemDataKey(dao.getId().toString(), getCacheEntityName()));
        removeIndex(Collections.singletonList(dao));
        return deleted;
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
        Set<String> keys = redisTemplate.keys(RedisUtils.getCacheItemDataKeyWildcard(getCacheEntityName()));
        if (CollectionUtils.isNotEmpty(keys)) {
            redisTemplate.delete(keys);
        }
        sequenceProcessor.removeAllIndex(getCacheEntityName());
        characterProcessor.removeAllIndex(getCacheEntityName());
        
    }
    
    protected Boolean hitCache(List<SearchCriteria> searchCriteriaList) {
        
        List<String> cacheIllegibleFields = getCacheIllegibleFields().values().stream().map(Field::getName).collect(Collectors.toList());
        
        for (SearchCriteria criteria : searchCriteriaList) {
            if (criteria.getKeys().size() > 1 || !cacheIllegibleFields.contains(criteria.getKey())) {
                return false;
            }
        }
        
        log.trace("Criteria {} hit cache policy, will query data in cache", searchCriteriaList);
        return true;
    }
    
    @Override
    public void initCacheData() {
        
        List<DAO> all = underlyingService.findAll(null).stream().map(this::convertToDao).collect(Collectors.toList());
        Map<String, DAO> collect = all.stream()
                .collect(Collectors.groupingBy(dao -> RedisUtils.getCacheItemDataKey(dao.getId().toString(), getCacheEntityName()),
                        Collectors.reducing((t1, t2) -> t1)))
                .entrySet().stream().map(entry -> Pair.of(entry.getKey(), entry.getValue().orElseThrow(BusinessException::new)))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
        redisTemplate.opsForValue().multiSet(collect);
        buildIndex(all);
        
    }
    
    private void buildIndex(List<DAO> daoList) {
        operateIndex(daoList, (key, value, ids) -> {
            getIndexProcessor(value).buildIndex(key, value, ids, getCacheEntityName());
        });
    }
    
    private void removeIndex(List<DAO> daoList) {
        operateIndex(daoList, (key, value, ids) -> {
            getIndexProcessor(value).removeIndex(key, value, ids, getCacheEntityName());
        });
    }
    
    private void operateIndex(List<DAO> daoList, TriConsumer<String, Object, Set<ID>> consumer) {
        getCacheIllegibleFields().values().forEach(field -> {
            Map<Pair<String, Object>, Set<ID>> collect = daoList.stream()
                    .map(dao -> Pair.of(Pair.of(field.getName(), ReflectionUtils.getValue(field, dao)), dao.getId()))
                    .collect(Collectors.groupingBy(Pair::getLeft, Collectors.mapping(Pair::getRight, Collectors.toSet())));
            collect.forEach((pair, idSet) -> {
                if (Objects.isNull(pair.getRight())) {
                    throw new BusinessException("NULL_POINTER_EXCEPTION", String.format("索引字段[%s]不允许为空值", pair.getLeft()));
                }
                consumer.accept(pair.getLeft(), pair.getRight(), idSet);
            });
        });
    }
    
    private Map<String, Field> getCacheIllegibleFields() {
        return FieldUtils.getFieldsListWithAnnotation(getDaoClass(), CacheQueryField.class).stream().collect(Collectors.toMap(Field::getName, v -> v));
    }
    
    @Override
    public String getCacheEntityName() {
        return Optional.ofNullable(AnnotationUtils.findAnnotation(getDaoClass(), CacheEntity.class)).map(CacheEntity::name)
                .orElse(getDaoClass().getSimpleName());
    }
    
    @Override
    public Integer getDaysOfCachedData() {
        return 10;
    }
    
}
