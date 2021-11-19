package xyz.mayday.tools.bunny.ddd.workflow.activity;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.METHOD })
public @interface CompensateMethod {
}
