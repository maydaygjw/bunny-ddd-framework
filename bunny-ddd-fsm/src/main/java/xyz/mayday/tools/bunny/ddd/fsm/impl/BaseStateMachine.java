package xyz.mayday.tools.bunny.ddd.fsm.impl;

import org.squirrelframework.foundation.fsm.impl.AbstractStateMachine;
import xyz.mayday.tools.bunny.ddd.fsm.action.ActionFactory;
import xyz.mayday.tools.bunny.ddd.fsm.action.ActionType;
import xyz.mayday.tools.bunny.ddd.fsm.context.FSMContext;
import xyz.mayday.tools.bunny.ddd.fsm.context.FSMSupport;
import xyz.mayday.tools.bunny.ddd.schema.exception.BusinessException;
import xyz.mayday.tools.bunny.ddd.schema.exception.FrameworkExceptionEnum;
import xyz.mayday.tools.bunny.ddd.schema.service.DistributedLock;
import xyz.mayday.tools.bunny.ddd.schema.service.ServiceFactory;

/**
 * @author gejunwen
 */
public class BaseStateMachine<DOMAIN extends FSMSupport<S>, T extends BaseStateMachine<DOMAIN, T, S, E, C>, S extends Enum<S>, E extends Enum<E>, C extends FSMContext<DOMAIN>> extends AbstractStateMachine<T, S, E, C> {

    DistributedLock lock;

    ActionFactory<S, E, DOMAIN, C> actionFactory;

    ServiceFactory serviceFactory;

    public S syncFire(S fromState, E event, C input) {
        if(super.canAccept(event)) {
            doCheckAndFire(fromState, event, input);
            return super.getCurrentState();
        } else {
            throw new BusinessException(FrameworkExceptionEnum.FRAMEWORK_EXCEPTION);
        }
    }

    @Override
    protected void afterTransitionCompleted(S fromState, S toState, E event, C context) {

        rollingNextState(toState, context);

        //executing the PRE actions
        actionFactory.fetchActions(fromState, event, ActionType.PRE).forEach(action -> {
            action.doAction(fromState, toState, event, context);
        });

        //persist state in DB
        DOMAIN saved = serviceFactory.getService(context.getPayload()).save(context.getPayload());
        context.setPayload(saved);

        actionFactory.fetchActions(fromState, event, ActionType.POST).forEach(action -> {
            action.doAction(fromState, toState, event, context);
        });

        doAfterTransactionCompleted(fromState, toState, event, context);
    }

    private void doCheckAndFire(S fromState, E event, C context) {
        // executing the PREPARE actions
        actionFactory.fetchActions(fromState, event, ActionType.PREPARE).forEach(action -> {
            action.doAction(fromState, null, event, context);
        });
        if(!super.canAccept(event)) {
            doAfterTransitionDeclined(getInitialState(), event, context);
        }
        this.fire(event);
    }

    @Override
    protected void afterTransitionDeclined(S fromState, E event, C context) {
        doAfterTransitionDeclined(fromState, event, context);
    }

    protected void doAfterTransitionDeclined(S fromState, E event, C context) {

    }

    protected boolean doAfterTransactionCompleted(S fromState, S toState, E event, C context) {
        return true;
    }

    protected void rollingNextState(S to, C context) {
        context.getPayload().setState(to);
    }
}