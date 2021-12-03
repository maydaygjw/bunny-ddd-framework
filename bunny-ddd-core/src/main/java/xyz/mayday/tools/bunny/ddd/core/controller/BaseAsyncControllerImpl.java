package xyz.mayday.tools.bunny.ddd.core.controller;

import xyz.mayday.tools.bunny.ddd.schema.controller.AsyncController;
import xyz.mayday.tools.bunny.ddd.schema.domain.AsyncResultVO;
import xyz.mayday.tools.bunny.ddd.schema.domain.BaseVO;
import xyz.mayday.tools.bunny.ddd.schema.page.PageableData;
import xyz.mayday.tools.bunny.ddd.schema.query.CommonQueryParam;

public abstract class BaseAsyncControllerImpl<ID, VO extends BaseVO<ID>, Q, DTO> extends BaseControllerImpl<ID, VO, Q, DTO> implements AsyncController<VO, Q> {
    @Override
    public AsyncResultVO queryItemsAsync(Q query, CommonQueryParam req) {
        return null;
    }
    
    @Override
    public PageableData<VO> queryAsyncResult(String ticket) {
        return null;
    }
    
    @Override
    public AsyncResultVO createAsync(VO vo) {
        return null;
    }
}
