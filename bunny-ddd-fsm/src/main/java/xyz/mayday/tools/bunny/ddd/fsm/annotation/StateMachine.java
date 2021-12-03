package xyz.mayday.tools.bunny.ddd.fsm.annotation;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** @author gejunwen */
@Target(TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface StateMachine {
    
    String id() default "";
}
