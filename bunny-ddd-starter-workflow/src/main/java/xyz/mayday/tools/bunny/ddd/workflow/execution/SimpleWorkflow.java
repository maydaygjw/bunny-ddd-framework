package xyz.mayday.tools.bunny.ddd.workflow.execution;

import java.lang.reflect.Method;
import java.time.Duration;

import org.apache.commons.lang3.reflect.MethodUtils;

import com.uber.cadence.activity.ActivityMethod;
import com.uber.cadence.activity.ActivityOptions;
import com.uber.cadence.internal.common.InternalUtils;
import com.uber.cadence.workflow.ActivityStub;
import com.uber.cadence.workflow.Workflow;
import com.uber.cadence.workflow.WorkflowMethod;

public interface SimpleWorkflow {
    
    @WorkflowMethod(executionStartToCloseTimeoutSeconds = 30, taskList = "test-task")
    ExecutionContext invokeActivity(Class<?> activitySpec, Object... args);
    
    class SimpleWorkFlowImpl implements SimpleWorkflow {
        
        @Override
        public ExecutionContext invokeActivity(Class<?> activitySpec, Object... args) {
            ActivityStub activityStub = Workflow.newUntypedActivityStub(new ActivityOptions.Builder().setScheduleToCloseTimeout(Duration.ofMinutes(1)).build());
            Method activityMethod = MethodUtils.getMethodsListWithAnnotation(activitySpec, ActivityMethod.class).get(0);
            String activityType = InternalUtils.getSimpleName(activityMethod);
            
            Object result = activityStub.execute(activityType, activityMethod.getReturnType(), args);
            ExecutionContext ctx = new ExecutionContext();
            ctx.setResult(result);
            return ctx;
        }
    }
}
