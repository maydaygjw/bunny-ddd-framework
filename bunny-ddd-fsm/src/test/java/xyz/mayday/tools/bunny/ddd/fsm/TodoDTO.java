package xyz.mayday.tools.bunny.ddd.fsm;

import lombok.*;
import xyz.mayday.tools.bunny.ddd.core.domain.AbstractBaseDTO;
import xyz.mayday.tools.bunny.ddd.fsm.context.FSMSupport;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@With
@Builder
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
