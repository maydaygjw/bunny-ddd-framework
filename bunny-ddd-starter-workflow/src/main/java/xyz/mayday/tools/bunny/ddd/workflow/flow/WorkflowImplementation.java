package xyz.mayday.tools.bunny.ddd.workflow.flow;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.TYPE })
public @interface WorkflowImplementation {
}
