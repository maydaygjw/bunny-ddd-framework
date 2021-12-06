package xyz.mayday.tools.bunny.ddd.schema.service;

/** @author gejunwen */
public interface CacheableService<ID, DOMAIN> extends BaseService<ID, DOMAIN> {
    
    BaseService<ID, DOMAIN> getUnderlyingService();
    
    void createCache();
    
    void destroyCache();
    
    void initCacheData();
    
    String getCacheEntityName();
}
