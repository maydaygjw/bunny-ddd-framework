package xyz.mayday.tools.bunny.ddd.cache.query;

import java.util.Set;

import xyz.mayday.tools.bunny.ddd.schema.query.SearchCriteria;

public interface IndexProcessor {
    
    Set<String> lookupIds(SearchCriteria criteria, String cacheName);
    
    void buildIndex(String key, Object value, Set<?> idSet, String cacheName);
    
    void removeIndex(String key, Object value, Set<?> idSet, String cacheName);
    
    void removeAllIndex(String cacheName);
}
