package xyz.mayday.tools.bunny.ddd.fsm.impl


import spock.lang.Specification
import xyz.mayday.tools.bunny.ddd.fsm.*
import xyz.mayday.tools.bunny.ddd.fsm.config.FSMDefinition

class BaseStateMachineTest extends Specification {

    def "test for SyncFire"() {
        given:
            def wrapper = new StateMachineBuilderWrapper(new FSMDefinition<TodoStateMachine, TodoDTO, TodoState, TodoEvent, TodoContext>(TodoStateMachine.class, TodoEvent.class, TodoState.class, TodoContext.class))
        when:
            wrapper.generateStateMachineDefinition()
        then:
            wrapper.transitionDefinitions.size() == 3

    }

}
