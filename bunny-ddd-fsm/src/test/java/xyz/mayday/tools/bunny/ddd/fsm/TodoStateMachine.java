package xyz.mayday.tools.bunny.ddd.fsm;

import xyz.mayday.tools.bunny.ddd.fsm.annotation.StateMachine;
import xyz.mayday.tools.bunny.ddd.fsm.annotation.Transit;
import xyz.mayday.tools.bunny.ddd.fsm.impl.BaseStateMachine;

@StateMachine
@Transit.Transitions(values = {
        @Transit(on = "START", from = "INIT", to = "DOING"),
        @Transit(on = "HALT", from = {"INIT", "DOING"}, to = "ABORT"),
        @Transit(on = "CHECK", from = "DOING"),
})
public class TodoStateMachine extends BaseStateMachine<TodoDTO, TodoStateMachine, TodoState, TodoEvent, TodoContext> {
}

