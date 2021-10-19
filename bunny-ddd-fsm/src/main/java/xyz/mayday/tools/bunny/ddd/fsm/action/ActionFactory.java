package xyz.mayday.tools.bunny.ddd.fsm.action;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationContext;

import xyz.mayday.tools.bunny.ddd.fsm.context.FSMContext;

public class ActionFactory<S, E, DOMAIN, C extends FSMContext<DOMAIN>> {
    
    Set<Action<S, E, DOMAIN, C>> actions;
    
    ApplicationContext ctx;
    
    @SuppressWarnings("unchecked")
    public ActionFactory(ApplicationContext ctx) {
        this.ctx = ctx;
        this.actions = new HashSet<>();
        Collection<Action> values = ctx.getBeansOfType(Action.class).values();
        actions.addAll((Collection) values);
    }
    
    public List<Action<S, E, DOMAIN, C>> fetchActions(S fromState, E event) {
        return null;
    }
    
    public List<Action<S, E, DOMAIN, C>> fetchActions(S fromState, E event, ActionType actionType) {
        return actions.stream().filter(action -> actionType.equals(action.getActionType()))
                .filter(action -> action.getMatchedConditions().stream().map(ActionCondition::getFromState).anyMatch(state -> state.equals(fromState)))
                .filter(action -> action.getMatchedConditions().stream().map(ActionCondition::getEvent).anyMatch(e -> e.equals(event)))
                .sorted(Comparator.comparingInt(Action::order)).collect(Collectors.toList());
    }
}
