package xyz.mayday.tools.bunny.ddd.schema.service;

import xyz.mayday.tools.bunny.ddd.schema.page.PageableData;
import xyz.mayday.tools.bunny.ddd.schema.query.CommonQueryParam;

public interface AsyncService<ID, DOMAIN> {
    
    String findItemsAsync(DOMAIN domain, CommonQueryParam commonQueryParam);
    
    String addItemAsync(DOMAIN domain);
    
    PageableData<DOMAIN> findQueryResultByTicket(String ticket);
}
