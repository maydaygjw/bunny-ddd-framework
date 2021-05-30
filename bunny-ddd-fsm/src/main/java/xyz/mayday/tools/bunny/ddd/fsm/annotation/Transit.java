package xyz.mayday.tools.bunny.ddd.fsm.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

@Target(TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Transit {

    String[] from();

    String to() default "";

    String on();

    String whenMvel() default "";

    String comment() default "";

    boolean active() default true;

    boolean implicit() default false;       //if implicit, will hide in generated chart

    @Target(TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface Transitions {
        Transit[] values();
    }
}


