package xyz.mayday.tools.bunny.ddd.fsm;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.mayday.tools.bunny.ddd.fsm.context.FSMContext;

@EqualsAndHashCode(callSuper = true)
@Data
public class TodoContext extends FSMContext<TodoDTO> {
}
