package xyz.mayday.tools.bunny.ddd.fsm.config;

import lombok.Data;

@Data
public class FSMActionDefinition {

    String fromState;

    String toState;

    String operationKey;

    String operationName;

    String mvel;
}
