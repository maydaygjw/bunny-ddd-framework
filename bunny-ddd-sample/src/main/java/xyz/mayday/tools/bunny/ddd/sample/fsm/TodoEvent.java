package xyz.mayday.tools.bunny.ddd.sample.fsm;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum TodoEvent {

    START,
    ABORT,
    COMPLETE,
    ;
}
