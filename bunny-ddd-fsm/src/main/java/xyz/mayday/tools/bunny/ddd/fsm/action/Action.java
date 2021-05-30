package xyz.mayday.tools.bunny.ddd.fsm.action;

import xyz.mayday.tools.bunny.ddd.fsm.context.FSMContext;

import java.util.Set;

public interface Action<S, E, DOMAIN, C extends FSMContext<DOMAIN>> {

    void doAction(S from, S to, E event, FSMContext<DOMAIN> context);

    int order();

    ActionType getActionType();

    Set<ActionCondition<S, E>> getMatchedConditions();

}
