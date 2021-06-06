package xyz.mayday.tools.bunny.ddd.fsm.impl;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.squirrelframework.foundation.fsm.StateMachineBuilder;
import org.squirrelframework.foundation.fsm.StateMachineBuilderFactory;
import xyz.mayday.tools.bunny.ddd.fsm.annotation.Transit;
import xyz.mayday.tools.bunny.ddd.fsm.config.FSMDefinition;
import xyz.mayday.tools.bunny.ddd.fsm.config.TransitDefinition;
import xyz.mayday.tools.bunny.ddd.fsm.context.FSMContext;
import xyz.mayday.tools.bunny.ddd.fsm.context.FSMSupport;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author gejunwen
 */
@Getter
public class StateMachineBuilderWrapper<DOMAIN extends FSMSupport<S>, T extends BaseStateMachine<DOMAIN, T, S, E, C>, S extends Enum<S>, E extends Enum<E>, C extends FSMContext<DOMAIN>> {

    StateMachineBuilder<T, S, E, C> builder;

    FSMDefinition<T, DOMAIN, S, E, C> stateMachineDefinition;

    List<TransitDefinition> transitionDefinitions;

    public StateMachineBuilderWrapper(FSMDefinition<T, DOMAIN, S, E, C> smDefinition) {
        this.stateMachineDefinition = smDefinition;
        this.builder = StateMachineBuilderFactory.create(smDefinition.getStateMachineClass(), smDefinition.getStateClass(), smDefinition.getEventClass(), smDefinition.getContextClass());

        generateStateMachineDefinition();
    }


    public T getStateMachineInstance(S initialState) {
        return builder.newStateMachine(initialState);
    }

    void generateStateMachineDefinition() {
        transitionDefinitions = Arrays.stream(Objects.requireNonNull(AnnotationUtils.findAnnotation(stateMachineDefinition.getStateMachineClass(), Transit.Transitions.class)).values())
                .filter(Transit::enabled)
                .flatMap(transit -> Arrays.stream(transit.from())
                .map(from -> new TransitDefinition().withFrom(from).withOn(transit.on()).withTo(transit.to()).withComment(transit.comment()).withImplicit(transit.implicit()).withEnabled(transit.enabled()).withWhenMvel(transit.whenMvel())))
                .peek(transitDef -> transitDef.setTo(StringUtils.defaultIfEmpty(transitDef.getTo(), transitDef.getFrom())))
                .collect(Collectors.toList());
    }
}
