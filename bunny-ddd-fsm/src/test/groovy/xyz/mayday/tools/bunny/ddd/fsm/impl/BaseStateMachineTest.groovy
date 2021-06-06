package xyz.mayday.tools.bunny.ddd.fsm.impl

import com.google.common.collect.ImmutableMap
import org.springframework.context.ApplicationContext
import spock.lang.Shared
import spock.lang.Specification
import xyz.mayday.tools.bunny.ddd.fsm.*
import xyz.mayday.tools.bunny.ddd.fsm.action.AbstractAction
import xyz.mayday.tools.bunny.ddd.fsm.action.Action
import xyz.mayday.tools.bunny.ddd.fsm.action.ActionCondition
import xyz.mayday.tools.bunny.ddd.fsm.config.FSMDefinition
import xyz.mayday.tools.bunny.ddd.schema.service.BaseService
import xyz.mayday.tools.bunny.ddd.schema.service.ServiceFactory

class BaseStateMachineTest extends Specification {

    @Shared
    StateMachineBuilderWrapper wrapper

    @Shared
    AbstractAction action;

    def setup() {
        def serviceFactory = Stub(ServiceFactory.class)
        def baseService = Stub(BaseService.class)
        def ctx = Stub(ApplicationContext.class)
        def action = Spy(AbstractAction.class)

        ctx.getBeansOfType(Action.class) >> ImmutableMap.of("action", action)

        serviceFactory.getService(_) >> baseService
        baseService.save(_ as TodoDTO) >> new TodoDTO()

        action.getMatchedConditions() >> Collections.singleton(ActionCondition.of(TodoState.INIT, TodoEvent.START))
        action.process(_, _, _, _) >> null

        wrapper = new StateMachineBuilderWrapper(new FSMDefinition(TodoStateMachine.class, TodoEvent.class, TodoState.class, TodoContext.class), serviceFactory, ctx)
    }

    def "test for SyncFire"() {
        given:
            def instance = wrapper.getStateMachineInstance(TodoState.INIT)
        when:
            def afterState = instance.syncFire(TodoState.INIT, TodoEvent.START, new TodoContext(null, new TodoDTO()))
        then:
            afterState == TodoState.DOING
    }

}
