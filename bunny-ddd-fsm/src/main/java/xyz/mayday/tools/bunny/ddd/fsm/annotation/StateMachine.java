package xyz.mayday.tools.bunny.ddd.fsm.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

/** @author gejunwen */
@Target(TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface StateMachine {

  String id() default "";
}
