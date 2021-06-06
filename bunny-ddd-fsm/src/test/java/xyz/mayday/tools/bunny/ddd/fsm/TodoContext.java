package xyz.mayday.tools.bunny.ddd.fsm;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.mayday.tools.bunny.ddd.fsm.context.FSMContext;

@EqualsAndHashCode(callSuper = true)
public class TodoContext extends FSMContext<TodoDTO> {

    public TodoContext(String key, TodoDTO payload) {
        super(key, payload);
    }



}
