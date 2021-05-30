package xyz.mayday.tools.bunny.ddd.fsm.action;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ActionCondition<S, E> {

    S fromState;

    E event;

}
