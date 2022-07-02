package xyz.mayday.tools.bunny.ddd.cache.query;

import static xyz.mayday.tools.bunny.ddd.schema.exception.FrameworkExceptionEnum.UNSUPPORTED_OPERATION;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.core.RedisTemplate;

import xyz.mayday.tools.bunny.ddd.schema.exception.BusinessException;
import xyz.mayday.tools.bunny.ddd.schema.query.SearchCriteria;
import xyz.mayday.tools.bunny.ddd.schema.query.SearchOperation;
import xyz.mayday.tools.bunny.ddd.utils.CollectionUtils;

import com.google.common.collect.Lists;

@RequiredArgsConstructor
public class SequenceIndexProcessor implements IndexProcessor {
    
    final RedisTemplate<String, Object> redisTemplate;
    
    @Override
    public Set<String> lookupIds(SearchCriteria criteria, String cacheName) {
        
        Set<String> resultIdList = new HashSet<>();
        
        if (Lists.newArrayList(SearchOperation.IN, SearchOperation.EQUAL).contains(criteria.getSearchOperation())) {
            double score = RedisUtils.calculateScore(criteria.getValue());
            criteria.getValues().forEach(value -> {
                Set<Object> ids = redisTemplate.opsForZSet().rangeByScore(RedisUtils.getCacheItemIndexKeyOfZSet(criteria.getKey(), cacheName), score, score);
                if (CollectionUtils.isNotEmpty(ids)) {
                    resultIdList
                            .addAll(ids.stream().map(Object::toString).map(id -> RedisUtils.getCacheItemDataKey(id, cacheName)).collect(Collectors.toSet()));
                }
            });
            
        } else if (SearchOperation.GREATER_THAN_EQUAL.equals(criteria.getSearchOperation())) {
            double score = RedisUtils.calculateScore(criteria.getValue());
            Set<Object> ids = redisTemplate.opsForZSet().rangeByScore(RedisUtils.getCacheItemIndexKeyOfZSet(criteria.getKey(), cacheName), score,
                    Double.MAX_VALUE);
            if (CollectionUtils.isNotEmpty(ids)) {
                resultIdList.addAll(ids.stream().map(Object::toString).map(id -> RedisUtils.getCacheItemDataKey(id, cacheName)).collect(Collectors.toSet()));
            }
        } else if (SearchOperation.LESS_THAN_EQUAL.equals(criteria.getSearchOperation())) {
            double score = RedisUtils.calculateScore(criteria.getValue());
            Set<Object> ids = redisTemplate.opsForZSet().rangeByScore(RedisUtils.getCacheItemIndexKeyOfZSet(criteria.getKey(), cacheName), Double.MIN_VALUE,
                    score);
            if (CollectionUtils.isNotEmpty(ids)) {
                resultIdList.addAll(ids.stream().map(Object::toString).map(id -> RedisUtils.getCacheItemDataKey(id, cacheName)).collect(Collectors.toSet()));
            }
        } else {
            throw new BusinessException(UNSUPPORTED_OPERATION);
        }
        
        return resultIdList;
    }
    
    @Override
    public void buildIndex(String key, Object value, Set<?> idSet, String cacheName) {
        String indexKey = RedisUtils.getCacheItemIndexKeyOfZSet(key, cacheName);
        double score = RedisUtils.calculateScore(value);
        idSet.forEach(id -> {
            redisTemplate.opsForZSet().add(indexKey, id, score);
        });
    }
    
    @Override
    public void removeIndex(String key, Object value, Set<?> idSet, String cacheName) {
        String indexKey = RedisUtils.getCacheItemIndexKeyOfZSet(key, cacheName);
        redisTemplate.opsForZSet().remove(indexKey, idSet.toArray());
    }
    
    @Override
    public void removeAllIndex(String cacheName) {
        Set<String> zSetIndexKeys = redisTemplate.keys(RedisUtils.getCacheItemIndexKeyOfZSet("*", cacheName));
        if (CollectionUtils.isNotEmpty(zSetIndexKeys)) {
            redisTemplate.delete(zSetIndexKeys);
        }
    }
}
