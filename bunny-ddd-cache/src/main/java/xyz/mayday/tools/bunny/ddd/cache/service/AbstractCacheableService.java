package xyz.mayday.tools.bunny.ddd.cache.service;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import lombok.extern.slf4j.Slf4j;
import xyz.mayday.tools.bunny.ddd.cache.query.*;
import xyz.mayday.tools.bunny.ddd.core.domain.AbstractBaseDTO;
import xyz.mayday.tools.bunny.ddd.core.service.AbstractBaseService;
import xyz.mayday.tools.bunny.ddd.schema.auth.PrincipalService;
import xyz.mayday.tools.bunny.ddd.schema.cache.CacheEntity;
import xyz.mayday.tools.bunny.ddd.schema.cache.CacheQueryField;
import xyz.mayday.tools.bunny.ddd.schema.converter.GenericConverter;
import xyz.mayday.tools.bunny.ddd.schema.domain.BaseDAO;
import xyz.mayday.tools.bunny.ddd.schema.exception.BusinessException;
import xyz.mayday.tools.bunny.ddd.schema.page.PageableData;
import xyz.mayday.tools.bunny.ddd.schema.query.CommonQueryParam;
import xyz.mayday.tools.bunny.ddd.schema.query.SearchConjunction;
import xyz.mayday.tools.bunny.ddd.schema.query.SearchCriteria;
import xyz.mayday.tools.bunny.ddd.schema.query.SearchOperation;
import xyz.mayday.tools.bunny.ddd.schema.service.BaseService;
import xyz.mayday.tools.bunny.ddd.schema.service.CacheableService;
import xyz.mayday.tools.bunny.ddd.schema.service.HistoryService;
import xyz.mayday.tools.bunny.ddd.schema.service.IdGenerator;
import xyz.mayday.tools.bunny.ddd.utils.CollectionUtils;
import xyz.mayday.tools.bunny.ddd.utils.ReflectionUtils;

@Slf4j
public abstract class AbstractCacheableService<ID extends Serializable, DTO extends AbstractBaseDTO<ID>, DAO extends BaseDAO<ID>>
        extends AbstractBaseService<ID, DTO, DAO> implements CacheableService<ID, DTO> {
    
    final AbstractBaseService<ID, DTO, DAO> underlyingService;
    
    @Value("${application.name}")
    String appName;
    
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    
    @Autowired
    RangeAvailableProcessor rangeAvailableProcessor;
    
    @Autowired
    CharacterizeProcessor characterizeProcessor;
    
    public AbstractCacheableService(AbstractBaseService<ID, DTO, DAO> underlyingService) {
        this.underlyingService = underlyingService;
    }
    
    public AbstractCacheableService(GenericConverter converter, PrincipalService principalService, IdGenerator<String> idGenerator,
            HistoryService historyService, AbstractBaseService<ID, DTO, DAO> underlyingService) {
        super(converter, principalService, idGenerator, historyService);
        this.underlyingService = underlyingService;
    }
    
    @Override
    public Optional<DTO> findItemById(ID id) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(RedisUtils.getCacheItemDataKey(id.toString(), appName, getCacheEntityName())))
                .map(dao -> convertToDto((DAO) dao));
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
    
    @SuppressWarnings("unchecked")
    @Override
    public List<DTO> findAll(DTO example) {
        
        List<SearchCriteria> searchCriteriaList = buildQuerySpecification(example).getSearchCriteriaList();
        
        if (hitCache(searchCriteriaList)) {
            
            Set<DAO> queryResult = new HashSet<>();
            
            for (int i = 0; i < searchCriteriaList.size(); i++) {
                SearchCriteria criteria = searchCriteriaList.get(i);
                Set<String> idList = getProcessor(criteria.getValue()).findIdList(redisTemplate, criteria, getCacheEntityName());
                // find data by id
                Set<DAO> dataList = Objects.requireNonNull(redisTemplate.opsForValue().multiGet(idList)).stream().map(value -> (DAO) value)
                        .collect(Collectors.toSet());
                if (i == 0) {
                    queryResult.addAll(dataList);
                } else {
                    queryResult = Sets.intersection(queryResult, dataList);
                }
                
                if (CollectionUtils.isEmpty(queryResult)) {
                    break;
                }
            }
            
            return queryResult.stream().map(this::convertToDto).collect(Collectors.toList());
            
        } else {
            return underlyingService.findAll(example);
        }
    }
    
    private IdLookupProcessor getProcessor(Object value) {
        if (NumberUtils.isCreatable(value.toString()) || TypeUtils.isInstance(value, Date.class)) {
            return rangeAvailableProcessor;
        } else {
            return characterizeProcessor;
        }
    }
    
    @Override
    public Stream<DTO> findStream(DTO example) {
        return null;
    }
    
    @Transactional
    @Override
    public DTO insert(DTO dto) {
        DTO insert = underlyingService.insert(dto);
        DAO dao = convertToDao(insert);
        redisTemplate.opsForValue().set(RedisUtils.getCacheItemDataKey(dao.getId().toString(), appName, getCacheEntityName()), dao);
        buildIndex(Collections.singletonList(dao));
        return insert;
    }
    
    @Override
    public List<DTO> bulkInsert(List<DTO> dtos) {
        return null;
    }
    
    @Transactional
    @SuppressWarnings("unchecked")
    @Override
    public DTO update(DTO dto) {
        DTO update = underlyingService.update(dto);
        DAO dao = convertToDao(update);
        DAO oldOne = (DAO) redisTemplate.opsForValue().getAndSet(RedisUtils.getCacheItemDataKey(dao.getId().toString(), appName, getCacheEntityName()), dao);
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
        redisTemplate.delete(RedisUtils.getCacheItemDataKey(dao.getId().toString(), appName, getCacheEntityName()));
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
        Set<String> keys = redisTemplate.keys(RedisUtils.getCacheItemDataKeyWildcard(appName, getCacheEntityName()));
        if (CollectionUtils.isNotEmpty(keys)) {
            redisTemplate.delete(keys);
        }
        Set<String> indexKeys = redisTemplate.keys(RedisUtils.getCacheItemIndexKeyWildcard(appName, getCacheEntityName()));
        if (CollectionUtils.isNotEmpty(indexKeys)) {
            redisTemplate.delete(indexKeys);
        }
    }
    
    protected Boolean hitCache(List<SearchCriteria> searchCriteriaList) {
        
        List<String> cacheIllegibleFields = getCacheIllegibleFields().values().stream().map(Field::getName).collect(Collectors.toList());
        
        for (SearchCriteria criteria : searchCriteriaList) {
            if (criteria.getKeys().size() > 1 || !cacheIllegibleFields.contains(criteria.getKey())) {
                return false;
            }
        }
        
        log.debug("Hit cache policy, will query data in cache");
        return true;
    }
    
    @Override
    public void initCacheData() {
        
        List<DAO> all = underlyingService.findAll(null).stream().map(this::convertToDao).collect(Collectors.toList());
        Map<String, DAO> collect = all.stream()
                .collect(Collectors.groupingBy(dao -> RedisUtils.getCacheItemDataKey(dao.getId().toString(), appName, getCacheEntityName()),
                        Collectors.reducing((t1, t2) -> t1)))
                .entrySet().stream().map(entry -> Pair.of(entry.getKey(), entry.getValue().orElseThrow(BusinessException::new)))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
        redisTemplate.opsForValue().multiSet(collect);
        buildIndex(all);
        
    }
    
    private void buildIndex(List<DAO> daoList) {
        operateIndex(daoList, (pair, ids) -> {
            if (NumberUtils.isCreatable(pair.getRight().toString()) || TypeUtils.isInstance(pair.getRight(), Date.class)) {
                String indexKey = RedisUtils.getCacheItemIndexKeyOfZSet(pair.getLeft(), appName, getCacheEntityName());
                double score = RedisUtils.calculateScore(pair.getRight());
                ids.forEach(id -> {
                    redisTemplate.opsForZSet().add(indexKey, id, score);
                });
            } else {
                String indexKey = RedisUtils.getCacheItemIndexKey(pair.getLeft(), pair.getRight().toString(), appName, getCacheEntityName());
                redisTemplate.opsForSet().add(indexKey, ids.toArray());
            }
            return null;
        });
    }
    
    private void removeIndex(List<DAO> daoList) {
        operateIndex(daoList, (pair, ids) -> {
            if (NumberUtils.isCreatable(pair.getRight().toString()) || TypeUtils.isInstance(pair.getRight(), Date.class)) {
                String indexKey = RedisUtils.getCacheItemIndexKeyOfZSet(pair.getLeft(), appName, getCacheEntityName());
                redisTemplate.opsForZSet().remove(indexKey, ids);
            } else {
                String indexKey = RedisUtils.getCacheItemIndexKey(pair.getLeft(), pair.getRight().toString(), appName, getCacheEntityName());
                redisTemplate.opsForSet().remove(indexKey, ids.toArray());
            }
            return null;
        });
    }
    
    private void operateIndex(List<DAO> daoList, BiFunction<Pair<String, Object>, Set<ID>, Long> operator) {
        getCacheIllegibleFields().values().forEach(field -> {
            Map<Pair<String, Object>, Set<ID>> collect = daoList.stream()
                    .map(dao -> Pair.of(Pair.of(field.getName(), ReflectionUtils.getValue(field, dao)), dao.getId()))
                    .collect(Collectors.groupingBy(Pair::getLeft, Collectors.mapping(Pair::getRight, Collectors.toSet())));
            collect.forEach(operator::apply);
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
