package xyz.mayday.tools.bunny.ddd.fsm.action;

import xyz.mayday.tools.bunny.ddd.fsm.context.FSMContext;

import java.util.List;

public class ActionFactory<S, E, DOMAIN, C extends FSMContext<DOMAIN>> {

    public List<Action<S, E, DOMAIN, C>> fetchActions(S fromState, E event) {
        return null;
    }

    public List<Action<S, E, DOMAIN, C>> fetchActions(S fromState, E event, ActionType actionType) {
        return null;
    }
}
