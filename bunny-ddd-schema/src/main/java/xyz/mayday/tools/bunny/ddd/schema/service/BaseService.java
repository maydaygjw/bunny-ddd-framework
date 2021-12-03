package xyz.mayday.tools.bunny.ddd.schema.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import xyz.mayday.tools.bunny.ddd.schema.page.PageableData;
import xyz.mayday.tools.bunny.ddd.schema.query.CommonQueryParam;

public interface BaseService<ID, DOMAIN> {
    
    Optional<DOMAIN> findItemById(ID id);
    
    List<DOMAIN> findItemsByIds(List<ID> id);
    
    List<DOMAIN> findHistoriesById(ID id);
    
    PageableData<DOMAIN> findItems(DOMAIN example, CommonQueryParam queryParam);
    
    Long countItems(DOMAIN example);
    
    List<DOMAIN> findAll(DOMAIN example);
    
    Stream<DOMAIN> findStream(DOMAIN example);
    
    DOMAIN insert(DOMAIN domain);
    
    List<DOMAIN> bulkInsert(List<DOMAIN> domains);
    
    DOMAIN update(DOMAIN domain);
    
    List<DOMAIN> bulkUpdate(List<DOMAIN> domains);
    
    DOMAIN save(DOMAIN domain);
    
    DOMAIN delete(ID id);
    
    List<DOMAIN> bulkDeleteById(List<ID> ids);
    
    List<DOMAIN> deleteAll();
    
    Class<DOMAIN> getDomainClass();
}
