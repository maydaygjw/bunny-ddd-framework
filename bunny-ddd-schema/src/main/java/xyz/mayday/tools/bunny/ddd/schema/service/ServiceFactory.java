package xyz.mayday.tools.bunny.ddd.schema.service;

import xyz.mayday.tools.bunny.ddd.schema.domain.BaseVO;
import xyz.mayday.tools.bunny.ddd.schema.rpc.RpcClientSpec;

public interface ServiceFactory {
    
    <DOMAIN> BaseService<?, DOMAIN> getService(DOMAIN domain);
    
    <DOMAIN> BaseService<?, DOMAIN> getService(Class<DOMAIN> domainClass);
    
    <DOMAIN> RpcClientSpec<?, ? extends BaseVO<?>, DOMAIN> getRpcClient(Class<DOMAIN> domainClass);
}
