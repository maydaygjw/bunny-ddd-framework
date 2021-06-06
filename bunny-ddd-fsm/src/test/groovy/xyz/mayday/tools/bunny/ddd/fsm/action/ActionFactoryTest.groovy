package xyz.mayday.tools.bunny.ddd.fsm.action

import com.google.common.collect.ImmutableMap
import org.springframework.context.ApplicationContext
import spock.lang.Shared
import spock.lang.Specification
import xyz.mayday.tools.bunny.ddd.fsm.TodoEvent
import xyz.mayday.tools.bunny.ddd.fsm.TodoState

class ActionFactoryTest extends Specification {

    @Shared
    ActionFactory actionFactory;

    def setup() {
        def ctx = Stub(ApplicationContext.class)
        def action = Spy(AbstractAction.class)

        ctx.getBeansOfType(Action.class) >> ImmutableMap.of("action", action)
        action.getMatchedConditions() >> Collections.singleton(ActionCondition.of(TodoState.INIT, TodoEvent.START))

        actionFactory = new ActionFactory<>(ctx)
    }

    def "FetchActions"() {
        when:
            def actions = actionFactory.fetchActions(TodoState.INIT, TodoEvent.START, ActionType.PRE)
        then:
            actions.size() == 1

    }
}
