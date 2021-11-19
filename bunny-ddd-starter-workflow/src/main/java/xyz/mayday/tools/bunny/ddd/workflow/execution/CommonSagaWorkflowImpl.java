package xyz.mayday.tools.bunny.ddd.workflow.execution;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.List;

import org.apache.commons.lang3.reflect.MethodUtils;

import com.uber.cadence.activity.ActivityOptions;
import com.uber.cadence.internal.common.InternalUtils;
import com.uber.cadence.workflow.ActivityStub;
import com.uber.cadence.workflow.Workflow;

import xyz.mayday.tools.bunny.ddd.workflow.activity.SagaMethod;

public class CommonSagaWorkflowImpl implements CommonSagaWorkflow {

    @Override
    public void executeSaga(List<Class<?>> compensatoryActivitySpecs, ExecutionContext executionContext) {
        compensatoryActivitySpecs.forEach(compensatoryActivityClass -> {

            List<Method> methods = MethodUtils.getMethodsListWithAnnotation(compensatoryActivityClass, SagaMethod.class);
            String activityType = InternalUtils.getSimpleName(methods.get(0));

            ActivityStub activityStub = Workflow.newUntypedActivityStub(new ActivityOptions.Builder().setScheduleToCloseTimeout(Duration.ofMinutes(1)).build());
            activityStub.execute(activityType, Void.class, new ExecutionContext() {
                @Override
                public int hashCode() {
                    return super.hashCode();
                }
            });
        });
    }

}
