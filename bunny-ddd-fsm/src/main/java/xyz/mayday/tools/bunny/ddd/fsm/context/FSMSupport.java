package xyz.mayday.tools.bunny.ddd.fsm.context;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface FSMSupport<S1 extends Enum<S1>> {

    @JsonIgnore
    S1 getState();

    void setState(S1 state);
}
