package xyz.mayday.tools.bunny.ddd.schema.rpc;

import xyz.mayday.tools.bunny.ddd.schema.controller.BaseController;
import xyz.mayday.tools.bunny.ddd.schema.domain.BaseVO;

public interface RpcClientSpec<ID, VO extends BaseVO<ID>, QUERY> extends BaseController<ID, VO, QUERY, Void> {
}
