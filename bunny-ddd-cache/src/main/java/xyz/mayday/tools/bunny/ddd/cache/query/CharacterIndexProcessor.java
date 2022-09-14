package xyz.mayday.tools.bunny.ddd.cache.query;

import java.util.*;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.redis.core.RedisTemplate;

import xyz.mayday.tools.bunny.ddd.schema.query.SearchCriteria;

@RequiredArgsConstructor
public class CharacterIndexProcessor implements IndexProcessor {
    
    final RedisTemplate<String, Object> redisTemplate;
    
    @Override
    public Set<String> lookupIds(SearchCriteria criteria, String cacheName) {
        
        String compareWith = criteria.getKey();
        Collection<?> values = criteria.getValues();
        
        Set<String> idList = new HashSet<>();
        
        for (Object value : values) {
            String pattern = QueryPredicate.presetPredicates.get(criteria.getSearchOperation()).buildPattern(value.toString());
            Set<String> keys = Optional.ofNullable(redisTemplate.keys(RedisUtils.getCacheItemIndexKey(compareWith, pattern, cacheName)))
                    .orElse(Collections.emptySet());
            if (CollectionUtils.isNotEmpty(keys)) {
                Set<String> union = redisTemplate.opsForSet().union(keys).stream().map(id -> RedisUtils.getCacheItemDataKey(id.toString(), cacheName))
                        .collect(Collectors.toSet());
                idList.addAll(union);
            }
        }
        
        return idList;
    }
    
    @Override
    public void buildIndex(String key, Object value, Set<?> idSet, String cacheName) {
        String indexKey = RedisUtils.getCacheItemIndexKey(key, value.toString(), cacheName);
        redisTemplate.opsForSet().add(indexKey, idSet.toArray());
    }
    
    @Override
    public void removeIndex(String key, Object value, Set<?> idSet, String cacheName) {
        String indexKey = RedisUtils.getCacheItemIndexKey(key, value.toString(), cacheName);
        redisTemplate.opsForSet().remove(indexKey, idSet.toArray());
    }
    
    @Override
    public void removeAllIndex(String cacheName) {
        Set<String> indexKeys = redisTemplate.keys(RedisUtils.getCacheItemIndexKeyWildcard(cacheName));
        if (CollectionUtils.isNotEmpty(indexKeys)) {
            redisTemplate.delete(indexKeys);
        }
    }
}
