package xyz.mayday.tools.bunny.ddd.fsm.action;

import lombok.extern.slf4j.Slf4j;
import xyz.mayday.tools.bunny.ddd.core.converter.SimpleConverter;
import xyz.mayday.tools.bunny.ddd.fsm.context.FSMContext;
import xyz.mayday.tools.bunny.ddd.utils.DiffUtils;
import xyz.mayday.tools.bunny.ddd.utils.ReflectionUtils;

import java.util.Set;

@Slf4j
public abstract class AbstractAction<S, E, DOMAIN, C extends FSMContext<DOMAIN>> implements Action<S, E, DOMAIN, C>{

    public abstract void process(S from, S to, E event, DOMAIN payload);

    @Override
    public boolean predict(S from, S to, E event, FSMContext<DOMAIN> payload) {
        return true;
    }

    @Override
    public final void doAction(S from, S to, E event, FSMContext<DOMAIN> context) {
        log.trace("Executing {} of context {}, 【{}】", getClass().getSimpleName(), context.getPayload().getClass().getSimpleName(), context.getKey());
        Object old = SimpleConverter.clone(context.getPayload());
        process(from, to, event, context.getPayload());
        if(ReflectionUtils.hasProperty(old, "id") && ReflectionUtils.hasProperty(context.getPayload(), "id")) {
            log.trace("Complete execution of action |{}", DiffUtils.getDiffSummaryString(old, context.getPayload()));
        }

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
        return null;
    }
}
