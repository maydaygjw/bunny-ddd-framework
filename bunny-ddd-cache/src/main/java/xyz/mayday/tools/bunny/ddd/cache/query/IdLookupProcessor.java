package xyz.mayday.tools.bunny.ddd.cache.query;

import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;

import xyz.mayday.tools.bunny.ddd.schema.query.SearchCriteria;

public interface IdLookupProcessor {
    
    Set<String> findIdList(RedisTemplate<String, Object> redisTemplate, SearchCriteria criteria, String cacheName);
}
