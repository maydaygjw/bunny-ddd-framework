package xyz.mayday.tools.bunny.ddd.fsm.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import xyz.mayday.tools.bunny.ddd.fsm.context.FSMContext;
import xyz.mayday.tools.bunny.ddd.fsm.context.FSMSupport;
import xyz.mayday.tools.bunny.ddd.fsm.impl.BaseStateMachine;

@Data
@AllArgsConstructor
public class FSMDefinition<
    T extends BaseStateMachine<DOMAIN, T, S, E, C>,
    DOMAIN extends FSMSupport<S>,
    S extends Enum<S>,
    E extends Enum<E>,
    C extends FSMContext<DOMAIN>> {

  String machineId;

  Class<T> stateMachineClass;

  Class<E> eventClass;

  Class<S> stateClass;

  Class<C> contextClass;

  public FSMDefinition(
      Class<T> stateMachineClass, Class<E> eventClass, Class<S> stateClass, Class<C> contextClass) {
    this.machineId = stateMachineClass.getSimpleName();
    this.stateMachineClass = stateMachineClass;
    this.eventClass = eventClass;
    this.stateClass = stateClass;
    this.contextClass = contextClass;
  }
}
