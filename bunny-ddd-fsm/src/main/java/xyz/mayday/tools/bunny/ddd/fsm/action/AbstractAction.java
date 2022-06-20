package xyz.mayday.tools.bunny.ddd.fsm.action;

import java.util.Collections;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;
import xyz.mayday.tools.bunny.ddd.fsm.context.FSMContext;

@Slf4j
public abstract class AbstractAction<S, E, D, C extends FSMContext<D>> implements Action<S, E, D, C> {
    
    public abstract void process(S from, S to, E event, D payload);
    
    @Override
    public boolean predict(S from, S to, E event, FSMContext<D> payload) {
        return true;
    }
    
    @Override
    public final void doAction(S from, S to, E event, FSMContext<D> context) {
        process(from, to, event, context.getPayload());
    }
    
    @Override
    public int order() {
        return 10;
    }
    
    @Override
    public ActionType getActionType() {
        return ActionType.PRE;
    }
    
    @Override
    public Set<ActionCondition<S, E>> getMatchedConditions() {
        return Collections.emptySet();
    }
}
