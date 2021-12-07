package xyz.mayday.tools.bunny.ddd.cache.service;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import xyz.mayday.tools.bunny.ddd.cache.query.QueryPredicate;
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

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

@Slf4j
public abstract class AbstractCacheableService<ID extends Serializable, DTO extends AbstractBaseDTO<ID>, DAO extends BaseDAO<ID>>
        extends AbstractBaseService<ID, DTO, DAO> implements CacheableService<ID, DTO> {
    
    final AbstractBaseService<ID, DTO, DAO> underlyingService;
    
    @Value("${application.name}")
    String appName;
    
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    
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
        return Optional.ofNullable(redisTemplate.opsForValue().get(getCacheItemDataKey(id))).map(dao -> convertToDto((DAO) dao));
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
                String compareWith = criteria.getKey();
                Collection<?> values = criteria.getValues();
                Set<String> idList = new HashSet<>();
                for (Object value : values) {
                    String pattern = QueryPredicate.presetPredicates.get(criteria.getSearchOperation()).buildPattern(value.toString());
                    Set<String> keys = Optional.ofNullable(redisTemplate.keys(getCacheItemIndexKey(compareWith, pattern))).orElse(Collections.emptySet());
                    if (CollectionUtils.isNotEmpty(keys)) {
                        Set<String> union = redisTemplate.opsForSet().union(keys).stream().map(objId -> (ID) objId).map(this::getCacheItemDataKey)
                                .collect(Collectors.toSet());
                        idList.addAll(union);
                    }
                }
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
    
    @Override
    public Stream<DTO> findStream(DTO example) {
        return null;
    }
    
    @Transactional
    @Override
    public DTO insert(DTO dto) {
        DTO insert = underlyingService.insert(dto);
        DAO dao = convertToDao(insert);
        redisTemplate.opsForValue().set(getCacheItemDataKey(dao.getId()), dao);
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
        DAO oldOne = (DAO) redisTemplate.opsForValue().getAndSet(getCacheItemDataKey(dao.getId()), dao);
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
        Set<String> keys = redisTemplate.keys(getCacheItemDataKeyWildcard());
        if (CollectionUtils.isNotEmpty(keys)) {
            redisTemplate.delete(keys);
        }
        Set<String> indexKeys = redisTemplate.keys(getCacheItemIndexKeyWildcard());
        if (CollectionUtils.isNotEmpty(indexKeys)) {
            redisTemplate.delete(indexKeys);
        }
    }
    
    protected Boolean hitCache(List<SearchCriteria> searchCriteriaList) {
        
        List<String> cacheIllegibleFields = getCacheIllegibleFields().stream().map(Field::getName).collect(Collectors.toList());
        
        for (SearchCriteria criteria : searchCriteriaList) {
            if (criteria.getKeys().size() > 1
                    || !Lists.newArrayList(SearchOperation.IN, SearchOperation.EQUAL, SearchOperation.MATCH).contains(criteria.getSearchOperation())
                    || !SearchConjunction.AND.equals(criteria.getSearchConjunction()) || !cacheIllegibleFields.contains(criteria.getKey())) {
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
                .collect(Collectors.groupingBy(dao -> this.getCacheItemDataKey(dao.getId()), Collectors.reducing((t1, t2) -> t1))).entrySet().stream()
                .map(entry -> Pair.of(entry.getKey(), entry.getValue().orElseThrow(BusinessException::new)))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
        redisTemplate.opsForValue().multiSet(collect);
        buildIndex(all);
        
    }
    
    private void buildIndex(List<DAO> daoList) {
        operateIndex(daoList, (key, ids) -> redisTemplate.opsForSet().add(key, ids.toArray()));
    }
    
    private void removeIndex(List<DAO> daoList) {
        operateIndex(daoList, (key, ids) -> redisTemplate.opsForSet().remove(key, ids.toArray()));
    }
    
    private void operateIndex(List<DAO> daoList, BiFunction<String, Set<ID>, Long> operator) {
        List<Field> fieldsListWithAnnotation = getCacheIllegibleFields();
        fieldsListWithAnnotation.forEach(field -> {
            daoList.stream().map(dao -> {
                String indexKey = getCacheItemIndexKey(field.getName(), ReflectionUtils.getValue(field, dao).toString());
                return Pair.of(indexKey, dao.getId());
            }).collect(Collectors.groupingBy(Pair::getLeft, Collectors.mapping(Pair::getRight, Collectors.toSet()))).forEach(operator::apply);
        });
    }
    
    private List<Field> getCacheIllegibleFields() {
        return FieldUtils.getFieldsListWithAnnotation(getDaoClass(), CacheQueryField.class);
    }
    
    private String getCacheItemDataKeyWildcard() {
        String keyTemplate = "_data:${app.name}:${cache.name}:*:object";
        return StringSubstitutor.replace(keyTemplate, ImmutableMap.of("app.name", appName, "cache.name", getCacheEntityName()));
    }
    
    private String getCacheItemIndexKeyWildcard() {
        return getCacheItemIndexKey("*", "*");
    }
    
    private String getCacheItemDataKey(ID id) {
        String keyTemplate = "_data:${app.name}:${cache.name}:${data.id}:object";
        return StringSubstitutor.replace(keyTemplate, ImmutableMap.of("app.name", appName, "cache.name", getCacheEntityName(), "data.id", id));
    }
    
    private String getCacheItemIndexKey(String property, String value) {
        String indexKeyTemplate = "_idx:${app.name}:${cache.name}:${prop.name}:${prop.value}:set";
        return StringSubstitutor.replace(indexKeyTemplate,
                ImmutableMap.of("app.name", appName, "cache.name", getCacheEntityName(), "prop.name", property, "prop.value", value));
        
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
