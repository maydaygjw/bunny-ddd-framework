package xyz.mayday.tools.bunny.ddd.fsm.impl

import com.google.common.collect.ImmutableMap
import org.springframework.context.ApplicationContext
import spock.lang.Shared
import spock.lang.Specification
import xyz.mayday.tools.bunny.ddd.fsm.*
import xyz.mayday.tools.bunny.ddd.fsm.action.Action
import xyz.mayday.tools.bunny.ddd.fsm.config.FSMDefinition
import xyz.mayday.tools.bunny.ddd.schema.service.ServiceFactory

class StateMachineBuilderWrapperTest extends Specification {

    @Shared
    def wrapper

    def setup() {
        def ctx = Stub(ApplicationContext.class)
        ctx.getBeansOfType(Action.class) >> ImmutableMap.of()
        wrapper = new StateMachineBuilderWrapper(
                new FSMDefinition<TodoStateMachine, TodoDTO, TodoState, TodoEvent, TodoContext>(TodoStateMachine.class, TodoEvent.class, TodoState.class, TodoContext.class),
                Mock(ServiceFactory.class),
                ctx
        )
    }

    def "GenerateStateMachineDefinition"() {
        when:
        wrapper.generateStateMachineDefinition()
        then:
        wrapper.transitionDefinitions.size() == 4
    }

    def "GetStateMachineInstance"() {
        when:
        def instance = wrapper.getStateMachineInstance(TodoState.DOING)
        then:
        instance.getInitialState() == TodoState.DOING

    }
}
