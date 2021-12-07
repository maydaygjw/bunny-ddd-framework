package xyz.mayday.tools.bunny.ddd.cache.query;

import java.util.*;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.core.RedisTemplate;

import xyz.mayday.tools.bunny.ddd.schema.query.SearchCriteria;
import xyz.mayday.tools.bunny.ddd.utils.CollectionUtils;

@RequiredArgsConstructor
public class CharacterizeProcessor implements IdLookupProcessor {
    
    final String appName;
    
    @Override
    public Set<String> findIdList(RedisTemplate<String, Object> redisTemplate, SearchCriteria criteria, String cacheName) {
        
        String compareWith = criteria.getKey();
        Collection<?> values = criteria.getValues();
        
        Set<String> idList = new HashSet<>();
        
        for (Object value : values) {
            String pattern = QueryPredicate.presetPredicates.get(criteria.getSearchOperation()).buildPattern(value.toString());
            Set<String> keys = Optional.ofNullable(redisTemplate.keys(RedisUtils.getCacheItemIndexKey(compareWith, pattern, appName, cacheName)))
                    .orElse(Collections.emptySet());
            if (CollectionUtils.isNotEmpty(keys)) {
                Set<String> union = redisTemplate.opsForSet().union(keys).stream().map(id -> RedisUtils.getCacheItemDataKey(id.toString(), appName, cacheName))
                        .collect(Collectors.toSet());
                idList.addAll(union);
            }
        }
        
        return idList;
    }
}
