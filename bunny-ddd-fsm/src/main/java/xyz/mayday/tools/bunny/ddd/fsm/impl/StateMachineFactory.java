package xyz.mayday.tools.bunny.ddd.fsm.impl;

import java.util.Map;

public class StateMachineFactory {

    Map<String, StateMachineBuilderWrapper> builderWrappers;

    public <S> BaseStateMachine getStateMachineInstance(String machineId, S initialState) {
        return null;
    }

}
