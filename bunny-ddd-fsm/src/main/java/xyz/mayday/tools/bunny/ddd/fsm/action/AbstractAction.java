package xyz.mayday.tools.bunny.ddd.fsm.action;

import lombok.extern.slf4j.Slf4j;
import xyz.mayday.tools.bunny.ddd.fsm.context.FSMContext;
import xyz.mayday.tools.bunny.ddd.schema.converter.GenericConverter;
import xyz.mayday.tools.bunny.ddd.utils.DiffUtils;

import java.util.Set;

@Slf4j
public abstract class AbstractAction<S, E, DOMAIN, C extends FSMContext<DOMAIN>> implements Action<S, E, DOMAIN, C>{

    GenericConverter genericConverter;

    public AbstractAction(GenericConverter genericConverter) {
        this.genericConverter = genericConverter;
    }

    abstract void process(S from, S to, E event, DOMAIN payload);

    boolean filter(S from, S to, E event, DOMAIN payload) {
        return true;
    }

    @Override
    public final void doAction(S from, S to, E event, FSMContext<DOMAIN> context) {
        log.trace("Executing {} of context {}, 【{}】", getClass().getSimpleName(), context.getPayload().getClass().getSimpleName(), context.getKey());
        Object old = genericConverter.clone(context.getPayload());
        process(from, to, event, context.getPayload());
        log.trace("Complete execution of action |{}", DiffUtils.getDiffSummaryString(old, context.getPayload()));
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
