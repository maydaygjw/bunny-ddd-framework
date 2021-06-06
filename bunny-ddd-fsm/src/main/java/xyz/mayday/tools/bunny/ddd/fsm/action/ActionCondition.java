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

    public static <S, E> ActionCondition<S, E> of(S fromState, E event) {
        return new ActionCondition<>(fromState, event);
    }

}
