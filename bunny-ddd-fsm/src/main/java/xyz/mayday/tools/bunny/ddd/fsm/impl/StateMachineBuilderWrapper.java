package xyz.mayday.tools.bunny.ddd.fsm.impl;

import org.squirrelframework.foundation.fsm.StateMachineBuilder;
import xyz.mayday.tools.bunny.ddd.fsm.config.FSMDefinition;
import xyz.mayday.tools.bunny.ddd.fsm.context.FSMContext;
import xyz.mayday.tools.bunny.ddd.fsm.context.FSMSupport;

public class StateMachineBuilderWrapper<DOMAIN extends FSMSupport<S>, T extends BaseStateMachine<DOMAIN, T, S, E, C>, S extends Enum<S>, E extends Enum<E>, C extends FSMContext<DOMAIN>> {

    StateMachineBuilder<T, S, E, C> builder;

    FSMDefinition<T, DOMAIN, S, E, C> stateMachineDefinition;


    public T getStateMachineInstance(S initialState) {
        return builder.newStateMachine(initialState);
    }
}
