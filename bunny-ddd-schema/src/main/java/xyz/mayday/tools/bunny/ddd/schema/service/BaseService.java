package xyz.mayday.tools.bunny.ddd.schema.service;

import xyz.mayday.tools.bunny.ddd.schema.query.CommonQueryParam;
import xyz.mayday.tools.bunny.ddd.schema.page.PageableData;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface BaseService<ID, DOMAIN> {

    Optional<DOMAIN> findItemById(ID id);

    List<DOMAIN> findItemsByIds(List<ID> id);

    List<DOMAIN> findHistoriesById(ID id);

    PageableData<DOMAIN> findItems(DOMAIN example, CommonQueryParam queryParam);

    Long countItems(DOMAIN example);

    List<DOMAIN> findAll(DOMAIN example);

    Stream<DOMAIN> findStream(DOMAIN example);

    DOMAIN insert(DOMAIN domain);

    DOMAIN update(DOMAIN domain);

    DOMAIN save(DOMAIN domain);

    DOMAIN delete(ID id);

    List<DOMAIN> bulkDeleteById(List<ID> ids);

    List<DOMAIN> deleteAll();

    Class<DOMAIN> getDomainClass();

}
