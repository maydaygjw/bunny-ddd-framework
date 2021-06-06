package xyz.mayday.tools.bunny.ddd.fsm.impl

import spock.lang.Specification
import xyz.mayday.tools.bunny.ddd.fsm.TodoContext
import xyz.mayday.tools.bunny.ddd.fsm.TodoDTO
import xyz.mayday.tools.bunny.ddd.fsm.TodoEvent
import xyz.mayday.tools.bunny.ddd.fsm.TodoState
import xyz.mayday.tools.bunny.ddd.fsm.TodoStateMachine
import xyz.mayday.tools.bunny.ddd.fsm.config.FSMDefinition

class StateMachineBuilderWrapperTest extends Specification {

    def "GenerateStateMachineDefinition"() {
        given:
            def wrapper = new StateMachineBuilderWrapper(new FSMDefinition<TodoStateMachine, TodoDTO, TodoState, TodoEvent, TodoContext>(TodoStateMachine.class, TodoEvent.class, TodoState.class, TodoContext.class))
        when:
            wrapper.generateStateMachineDefinition()
        then:
            wrapper.transitionDefinitions.size() == 3
    }
}
