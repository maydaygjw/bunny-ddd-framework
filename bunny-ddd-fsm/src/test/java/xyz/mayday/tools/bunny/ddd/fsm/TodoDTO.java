package xyz.mayday.tools.bunny.ddd.fsm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import xyz.mayday.tools.bunny.ddd.core.domain.AbstractBaseDTO;
import xyz.mayday.tools.bunny.ddd.fsm.context.FSMSupport;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoDTO extends AbstractBaseDTO<String> implements FSMSupport<TodoState> {

    TodoState todoState;

    @Override
    public TodoState getState() {
        return todoState;
    }

    @Override
    public void setState(TodoState state) {
        setTodoState(state);
    }
}
