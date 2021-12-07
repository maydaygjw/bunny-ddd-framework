package xyz.mayday.tools.bunny.ddd.cache.query;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.core.RedisTemplate;

import xyz.mayday.tools.bunny.ddd.schema.query.SearchCriteria;
import xyz.mayday.tools.bunny.ddd.schema.query.SearchOperation;
import xyz.mayday.tools.bunny.ddd.utils.CollectionUtils;

import com.google.common.collect.Lists;

@RequiredArgsConstructor
public class RangeAvailableProcessor implements IdLookupProcessor {
    
    final String appName;
    
    @Override
    public Set<String> findIdList(RedisTemplate<String, Object> redisTemplate, SearchCriteria criteria, String cacheName) {
        
        Set<String> resultIdList = new HashSet<>();
        
        if (Lists.newArrayList(SearchOperation.IN, SearchOperation.EQUAL).contains(criteria.getSearchOperation())) {
            double score = RedisUtils.calculateScore(criteria.getValue());
            criteria.getValues().forEach(value -> {
                Set<Object> ids = redisTemplate.opsForZSet().rangeByScore(RedisUtils.getCacheItemIndexKeyOfZSet(criteria.getKey(), appName, cacheName), score,
                        score);
                if (CollectionUtils.isNotEmpty(ids)) {
                    resultIdList.addAll(ids.stream().map(Object::toString).collect(Collectors.toSet()));
                }
            });
            
        } else if (SearchOperation.GREATER_THAN_EQUAL.equals(criteria.getSearchOperation())) {
            double score = RedisUtils.calculateScore(criteria.getValue());
            Set<Object> ids = redisTemplate.opsForZSet().rangeByScore(RedisUtils.getCacheItemIndexKeyOfZSet(criteria.getKey(), appName, cacheName), score,
                    Double.MAX_VALUE);
            if (CollectionUtils.isNotEmpty(ids)) {
                resultIdList.addAll(ids.stream().map(Object::toString).collect(Collectors.toSet()));
            }
        }
        
        return resultIdList;
    }
}
