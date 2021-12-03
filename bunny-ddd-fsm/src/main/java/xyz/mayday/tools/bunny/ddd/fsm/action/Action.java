package xyz.mayday.tools.bunny.ddd.fsm.action;

import java.util.Set;

import xyz.mayday.tools.bunny.ddd.fsm.context.FSMContext;

public interface Action<S, E, DOMAIN, C extends FSMContext<DOMAIN>> {
    
    boolean predict(S from, S to, E event, FSMContext<DOMAIN> payload);
    
    void doAction(S from, S to, E event, FSMContext<DOMAIN> context);
    
    int order();
    
    ActionType getActionType();
    
    Set<ActionCondition<S, E>> getMatchedConditions();
}
