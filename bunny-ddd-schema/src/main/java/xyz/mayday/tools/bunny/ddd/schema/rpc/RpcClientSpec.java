package xyz.mayday.tools.bunny.ddd.schema.rpc;

import xyz.mayday.tools.bunny.ddd.schema.domain.BaseVO;
import xyz.mayday.tools.bunny.ddd.schema.http.Response;
import xyz.mayday.tools.bunny.ddd.schema.page.PageableData;
import xyz.mayday.tools.bunny.ddd.schema.query.CommonQueryParam;

public interface RpcClientSpec<ID, VO extends BaseVO<ID>, QUERY> {
    
    Response<PageableData<VO>> queryItems(QUERY query, CommonQueryParam commonQueryParam);
}
