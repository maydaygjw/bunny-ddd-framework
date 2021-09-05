package xyz.mayday.tools.bunny.ddd.fsm.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.Getter;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.squirrelframework.foundation.fsm.StateMachineBuilder;
import org.squirrelframework.foundation.fsm.StateMachineBuilderFactory;
import org.squirrelframework.foundation.fsm.builder.On;
import xyz.mayday.tools.bunny.ddd.fsm.annotation.Transit;
import xyz.mayday.tools.bunny.ddd.fsm.config.FSMDefinition;
import xyz.mayday.tools.bunny.ddd.fsm.config.TransitDefinition;
import xyz.mayday.tools.bunny.ddd.fsm.context.FSMContext;
import xyz.mayday.tools.bunny.ddd.fsm.context.FSMSupport;
import xyz.mayday.tools.bunny.ddd.schema.service.ServiceFactory;

/** @author gejunwen */
@Getter
public class StateMachineBuilderWrapper<
    DOMAIN extends FSMSupport<S>,
    T extends BaseStateMachine<DOMAIN, T, S, E, C>,
    S extends Enum<S>,
    E extends Enum<E>,
    C extends FSMContext<DOMAIN>> {

  StateMachineBuilder<T, S, E, C> builder;

  FSMDefinition<T, DOMAIN, S, E, C> stateMachineDefinition;

  List<TransitDefinition> transitionDefinitions;

  ServiceFactory serviceFactory;

  ApplicationContext ctx;

  public StateMachineBuilderWrapper(
      FSMDefinition<T, DOMAIN, S, E, C> smDefinition,
      ServiceFactory serviceFactory,
      ApplicationContext ctx) {
    this.ctx = ctx;
    this.stateMachineDefinition = smDefinition;
    this.serviceFactory = serviceFactory;
    this.builder =
        StateMachineBuilderFactory.create(
            smDefinition.getStateMachineClass(),
            smDefinition.getStateClass(),
            smDefinition.getEventClass(),
            smDefinition.getContextClass(),
            ApplicationContext.class,
            ServiceFactory.class);

    generateStateMachineDefinition();
    defineStateMachine();
  }

  void defineStateMachine() {
    getTransitionDefinitions()
        .forEach(
            def -> {
              On<T, S, E, C> on =
                  builder
                      .externalTransition()
                      .from(
                          EnumUtils.getEnum(stateMachineDefinition.getStateClass(), def.getFrom()))
                      .to(EnumUtils.getEnum(stateMachineDefinition.getStateClass(), def.getTo()))
                      .on(EnumUtils.getEnum(stateMachineDefinition.getEventClass(), def.getOn()));
              if (StringUtils.isNotBlank(def.getWhenMvel())) {
                on.whenMvel(def.getWhenMvel());
              }
            });
  }

  public T getStateMachineInstance(S initialState) {
    return builder.newStateMachine(initialState, ctx, serviceFactory);
  }

  void generateStateMachineDefinition() {
    transitionDefinitions =
        Arrays.stream(
                Objects.requireNonNull(
                        AnnotationUtils.findAnnotation(
                            stateMachineDefinition.getStateMachineClass(),
                            Transit.Transitions.class))
                    .values())
            .filter(Transit::enabled)
            .flatMap(
                transit ->
                    Arrays.stream(transit.from())
                        .map(
                            from ->
                                new TransitDefinition()
                                    .withFrom(from)
                                    .withOn(transit.on())
                                    .withTo(transit.to())
                                    .withComment(transit.comment())
                                    .withImplicit(transit.implicit())
                                    .withEnabled(transit.enabled())
                                    .withWhenMvel(transit.whenMvel())))
            .peek(
                transitDef ->
                    transitDef.setTo(
                        StringUtils.defaultIfEmpty(transitDef.getTo(), transitDef.getFrom())))
            .collect(Collectors.toList());
  }
}
