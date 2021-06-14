package xyz.mayday.tools.bunny.ddd.fsm.service;

import xyz.mayday.tools.bunny.ddd.fsm.context.FSMContext;
import xyz.mayday.tools.bunny.ddd.fsm.context.FSMSupport;
import xyz.mayday.tools.bunny.ddd.fsm.impl.BaseStateMachine;
import xyz.mayday.tools.bunny.ddd.schema.service.BaseService;

public interface FSMTrigger <ID, DOMAIN extends FSMSupport<S>, SM extends BaseStateMachine<DOMAIN, SM, S, E, C>, S extends Enum<S>, E extends Enum<E>, C extends FSMContext<DOMAIN>> {

    DOMAIN fire(E event, DOMAIN input);
}
