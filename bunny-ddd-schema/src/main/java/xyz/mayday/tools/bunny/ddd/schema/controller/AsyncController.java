package xyz.mayday.tools.bunny.ddd.schema.controller;

import xyz.mayday.tools.bunny.ddd.schema.domain.AsyncResultVO;
import xyz.mayday.tools.bunny.ddd.schema.page.PageableData;
import xyz.mayday.tools.bunny.ddd.schema.query.CommonQueryParam;

public interface AsyncController<VO, Q> {
    
    AsyncResultVO queryItemsAsync(Q query, CommonQueryParam req);
    
    PageableData<VO> queryAsyncResult(String ticket);
    
    AsyncResultVO createAsync(VO vo);
}
