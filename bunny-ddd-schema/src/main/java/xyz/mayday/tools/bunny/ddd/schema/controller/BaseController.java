package xyz.mayday.tools.bunny.ddd.schema.controller;

import xyz.mayday.tools.bunny.ddd.schema.domain.BaseVO;
import xyz.mayday.tools.bunny.ddd.schema.query.CommonQueryParam;
import xyz.mayday.tools.bunny.ddd.schema.page.PageableData;
import xyz.mayday.tools.bunny.ddd.schema.service.BaseService;

import java.util.List;
import java.util.Optional;

public interface BaseController<ID, VO extends BaseVO<ID>, QUERY, DOMAIN> {

    PageableData<VO> queryItems(QUERY query, CommonQueryParam commonQueryParam);

    VO create(VO vo);

    VO update(VO vo);

    VO delete(ID id);

    List<VO> bulkDelete(List<ID> ids);

    Optional<VO> queryById(ID id);

    List<VO> queryAll(CommonQueryParam commonQueryParam);

    List<VO> findHistories(ID id);

    Long countItems(QUERY query);

    BaseService<ID, DOMAIN> getService();

}
