package xyz.mayday.tools.bunny.ddd.fsm;

import org.squirrelframework.foundation.fsm.impl.AbstractStateMachine;
import xyz.mayday.tools.bunny.ddd.fsm.context.FSMContext;
import xyz.mayday.tools.bunny.ddd.schema.exception.BusinessException;
import xyz.mayday.tools.bunny.ddd.schema.exception.FrameworkExceptionEnum;

/**
 * @author gejunwen
 */
public class BaseStateMachine<DOMAIN, T extends BaseStateMachine<DOMAIN, T, S, E, C>, S extends Enum<S>, E extends Enum<E>, C extends FSMContext<DOMAIN>> extends AbstractStateMachine<T, S, E, C> {

    S checkAndFire(E event, DOMAIN input) {
        if(super.canAccept(event)) {
            super.fire(event);
            return super.getCurrentState();
        } else {
            throw new BusinessException(FrameworkExceptionEnum.FRAMEWORK_EXCEPTION);
        }
    }
}
