package xyz.mayday.tools.bunny.ddd.fsm.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class StateMachineFactory {

    private static StateMachineFactory instance;

    public static StateMachineFactory getInstance() {
        if(Objects.isNull(instance)) {
            instance = new StateMachineFactory();
            instance.builderWrappers = new HashMap<>(5);
        }

        return instance;
    }

    public void addStateMachine(String smId, StateMachineBuilderWrapper wrapper) {
        builderWrappers.put(smId, wrapper);
    }

    Map<String, StateMachineBuilderWrapper> builderWrappers;

    public <S extends Enum<S>> BaseStateMachine getStateMachineInstance(String machineId, S initialState) {
        return builderWrappers.get(machineId).getStateMachineInstance(initialState);
    }

}
