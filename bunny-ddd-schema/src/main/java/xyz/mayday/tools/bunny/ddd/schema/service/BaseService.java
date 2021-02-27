package xyz.mayday.tools.bunny.ddd.schema.service;

import xyz.mayday.tools.bunny.ddd.schema.query.CommonQueryParam;
import xyz.mayday.tools.bunny.ddd.schema.query.page.PageableData;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface BaseService<ID, DOMAIN> {

    Optional<DOMAIN> findItemById(ID id);

    PageableData<DOMAIN> findItems(DOMAIN example, CommonQueryParam queryParam);

    List<DOMAIN> findAll(DOMAIN example);

    Stream<DOMAIN> findStream(DOMAIN example);

    DOMAIN insert(DOMAIN domain);

    DOMAIN update(DOMAIN domain);

    DOMAIN save(DOMAIN domain);

    DOMAIN delete(DOMAIN domain);

    Class<DOMAIN> getDomainClass();

}
