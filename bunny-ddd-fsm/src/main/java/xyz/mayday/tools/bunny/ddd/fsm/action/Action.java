package xyz.mayday.tools.bunny.ddd.fsm.action;

import xyz.mayday.tools.bunny.ddd.fsm.context.FSMContext;

public interface Action<S, E, DOMAIN> {

    void process(S from, S to, E event, FSMContext<DOMAIN> context);
}
