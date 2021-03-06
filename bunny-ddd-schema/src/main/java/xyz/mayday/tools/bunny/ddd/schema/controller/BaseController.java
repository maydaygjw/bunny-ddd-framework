package xyz.mayday.tools.bunny.ddd.schema.controller;

import java.util.List;
import java.util.Optional;

import xyz.mayday.tools.bunny.ddd.schema.domain.BaseVO;
import xyz.mayday.tools.bunny.ddd.schema.http.Response;
import xyz.mayday.tools.bunny.ddd.schema.page.PageableData;
import xyz.mayday.tools.bunny.ddd.schema.query.CommonQueryParam;
import xyz.mayday.tools.bunny.ddd.schema.service.BaseService;

public interface BaseController<ID, VO extends BaseVO<ID>, QUERY, DOMAIN> {
    
    Response<PageableData<VO>> queryItems(QUERY query, CommonQueryParam commonQueryParam);
    
    VO create(VO vo);
    
    VO update(ID id, VO vo);
    
    VO delete(ID id);
    
    List<VO> bulkDelete(List<ID> ids);
    
    Optional<VO> queryById(ID id);
    
    Response<List<VO>> queryAll(QUERY query);
    
    List<VO> queryHistories(ID id);
    
    Long countItems(QUERY query);
    
    BaseService<ID, DOMAIN> getService();
}
