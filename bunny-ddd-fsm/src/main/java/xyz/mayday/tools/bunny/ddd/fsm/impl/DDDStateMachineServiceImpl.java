package xyz.mayday.tools.bunny.ddd.fsm.impl;

import xyz.mayday.tools.bunny.ddd.fsm.context.FSMContext;
import xyz.mayday.tools.bunny.ddd.fsm.context.FSMSupport;
import xyz.mayday.tools.bunny.ddd.fsm.service.FSMTrigger;
import xyz.mayday.tools.bunny.ddd.schema.page.PageableData;
import xyz.mayday.tools.bunny.ddd.schema.query.CommonQueryParam;
import xyz.mayday.tools.bunny.ddd.schema.service.BaseService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author gejunwen
 */
public class DDDStateMachineServiceImpl<ID, DOMAIN extends FSMSupport<S>, SM extends BaseStateMachine<DOMAIN, SM, S, E, C>, S extends Enum<S>, E extends Enum<E>, C extends FSMContext<DOMAIN>>
        implements BaseService<ID, DOMAIN>, FSMTrigger<ID, DOMAIN, SM, S, E, C> {

    BaseService<ID, DOMAIN> delegate;

    @Override
    public DOMAIN fire(E event, DOMAIN input) {
        return null;
    }

    @Override
    public Optional<DOMAIN> findItemById(ID id) {
        return Optional.empty();
    }

    @Override
    public List<DOMAIN> findItemsByIds(List<ID> id) {
        return null;
    }

    @Override
    public List<DOMAIN> findHistoriesById(ID id) {
        return null;
    }

    @Override
    public PageableData<DOMAIN> findItems(DOMAIN example, CommonQueryParam queryParam) {
        return null;
    }

    @Override
    public Long countItems(DOMAIN example) {
        return null;
    }

    @Override
    public List<DOMAIN> findAll(DOMAIN example) {
        return null;
    }

    @Override
    public Stream<DOMAIN> findStream(DOMAIN example) {
        return null;
    }

    @Override
    public DOMAIN insert(DOMAIN domain) {
        return null;
    }

    @Override
    public List<DOMAIN> bulkInsert(List<DOMAIN> domains) {
        return null;
    }

    @Override
    public DOMAIN update(DOMAIN domain) {
        return null;
    }

    @Override
    public List<DOMAIN> bulkUpdate(List<DOMAIN> domains) {
        return null;
    }

    @Override
    public DOMAIN save(DOMAIN domain) {
        return null;
    }

    @Override
    public DOMAIN delete(ID id) {
        return null;
    }

    @Override
    public List<DOMAIN> bulkDeleteById(List<ID> ids) {
        return null;
    }

    @Override
    public List<DOMAIN> deleteAll() {
        return null;
    }

    @Override
    public Class<DOMAIN> getDomainClass() {
        return delegate.getDomainClass();
    }
}
