package xyz.mayday.tools.bunny.ddd.schema.service;

/** @author gejunwen */
public interface CacheableService<ID, DOMAIN> extends BaseService<ID, DOMAIN> {
    
    void createCache();
    
    void destroyCache();
    
    void initCache();
}
