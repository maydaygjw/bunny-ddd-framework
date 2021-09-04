package xyz.mayday.tools.bunny.ddd.schema.service;

import xyz.mayday.tools.bunny.ddd.schema.domain.BaseVO;
import xyz.mayday.tools.bunny.ddd.schema.rpc.BaseRpcClient;

public interface ServiceFactory {

  <DOMAIN> BaseService<?, DOMAIN> getService(DOMAIN domain);

  <DOMAIN> BaseService<?, DOMAIN> getService(Class<DOMAIN> domainClass);

  <DOMAIN> BaseRpcClient<? extends BaseVO<?>, ?> getRpcClient(Class<DOMAIN> domainClass);
}
