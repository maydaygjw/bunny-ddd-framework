package xyz.mayday.tools.bunny.ddd.fsm.impl;

import java.util.Objects;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.squirrelframework.foundation.fsm.StateMachineLogger;
import org.squirrelframework.foundation.fsm.impl.AbstractStateMachine;
import xyz.mayday.tools.bunny.ddd.fsm.action.ActionFactory;
import xyz.mayday.tools.bunny.ddd.fsm.action.ActionType;
import xyz.mayday.tools.bunny.ddd.fsm.context.FSMContext;
import xyz.mayday.tools.bunny.ddd.fsm.context.FSMSupport;
import xyz.mayday.tools.bunny.ddd.schema.exception.BusinessException;
import xyz.mayday.tools.bunny.ddd.schema.exception.FrameworkExceptionEnum;
import xyz.mayday.tools.bunny.ddd.schema.service.DistributedLock;
import xyz.mayday.tools.bunny.ddd.schema.service.ServiceFactory;

/** @author gejunwen */
@Setter
@Slf4j
public class BaseStateMachine<
        DOMAIN extends FSMSupport<S>,
        T extends BaseStateMachine<DOMAIN, T, S, E, C>,
        S extends Enum<S>,
        E extends Enum<E>,
        C extends FSMContext<DOMAIN>>
    extends AbstractStateMachine<T, S, E, C> {

  DistributedLock lock;

  ServiceFactory serviceFactory;

  ActionFactory<S, E, DOMAIN, C> actionFactory;

  public S syncFire(S fromState, E event, C input) {
    if (super.canAccept(event)) {
      StateMachineLogger fsmLogger = null;
      if (log.isTraceEnabled()) {
        fsmLogger = new StateMachineLogger(this);
        fsmLogger.startLogging();
      }
      doCheckAndFire(fromState, event, input);
      if (log.isTraceEnabled()) {
        Objects.requireNonNull(fsmLogger).stopLogging();
      }
      return super.getCurrentState();
    } else {
      throw new BusinessException(FrameworkExceptionEnum.FRAMEWORK_EXCEPTION);
    }
  }

  @Override
  protected void afterTransitionCompleted(S fromState, S toState, E event, C context) {

    rollingNextState(toState, context);

    // executing the PRE actions
    actionFactory
        .fetchActions(fromState, event, ActionType.PRE)
        .forEach(
            action -> {
              action.doAction(fromState, toState, event, context);
            });

    // persist state in DB
    DOMAIN saved = serviceFactory.getService(context.getPayload()).save(context.getPayload());
    context.setPayload(saved);

    actionFactory
        .fetchActions(fromState, event, ActionType.POST)
        .forEach(
            action -> {
              action.doAction(fromState, toState, event, context);
            });

    doAfterTransactionCompleted(fromState, toState, event, context);
  }

  private void doCheckAndFire(S fromState, E event, C context) {
    // executing the PREPARE actions
    actionFactory
        .fetchActions(fromState, event, ActionType.PREPARE)
        .forEach(
            action -> {
              if (action.predict(fromState, null, event, context))
                action.doAction(fromState, null, event, context);
            });
    if (!super.canAccept(event)) {
      doAfterTransitionDeclined(getInitialState(), event, context);
    }
    this.fire(event, context);
  }

  @Override
  protected void afterTransitionDeclined(S fromState, E event, C context) {
    doAfterTransitionDeclined(fromState, event, context);
  }

  protected void doAfterTransitionDeclined(S fromState, E event, C context) {}

  protected boolean doAfterTransactionCompleted(S fromState, S toState, E event, C context) {
    return true;
  }

  protected void rollingNextState(S to, C context) {
    context.getPayload().setState(to);
  }

  // Squirrel framework hook to inject parameters
  void postConstruct(ApplicationContext ctx, ServiceFactory serviceFactory) {
    this.serviceFactory = serviceFactory;
    actionFactory = new ActionFactory<>(ctx);
  }
}
