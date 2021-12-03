package xyz.mayday.tools.bunny.ddd.workflow.execution;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.List;

import lombok.SneakyThrows;

import org.apache.commons.lang3.reflect.MethodUtils;

import xyz.mayday.tools.bunny.ddd.workflow.activity.CompensateMethod;
import xyz.mayday.tools.bunny.ddd.workflow.activity.SagaMethod;

import com.uber.cadence.activity.ActivityOptions;
import com.uber.cadence.internal.common.InternalUtils;
import com.uber.cadence.workflow.*;

public class CommonSagaWorkflowImpl implements CommonSagaWorkflow {
    
    @Override
    public void executeSaga(List<Class<?>> compensatoryActivitySpecs, ExecutionContext executionContext) {
        
        Saga.Options sagaOptions = new Saga.Options.Builder().setParallelCompensation(true).build();
        Saga saga = new Saga(sagaOptions);
        
        try {
            compensatoryActivitySpecs.forEach(compensatoryActivityClass -> {
                
                List<Method> methods = MethodUtils.getMethodsListWithAnnotation(compensatoryActivityClass, SagaMethod.class);
                String activityType = InternalUtils.getSimpleName(methods.get(0));
                
                List<Method> compensateMethods = MethodUtils.getMethodsListWithAnnotation(compensatoryActivityClass, CompensateMethod.class);
                Method compensateMethod = compensateMethods.get(0);
                
                ActivityStub activityStub = Workflow
                        .newUntypedActivityStub(new ActivityOptions.Builder().setScheduleToCloseTimeout(Duration.ofMinutes(1)).build());
                ExecutionContext context = activityStub.execute(activityType, ExecutionContext.class, new ExecutionContext());
                
                saga.addCompensation(new Functions.Func1<ExecutionContext, ExecutionContext>() {
                    @SneakyThrows
                    @Override
                    public ExecutionContext apply(ExecutionContext executionContext) {
                        String compensateActivityType = InternalUtils.getSimpleName(compensateMethod);
                        return activityStub.execute(compensateActivityType, ExecutionContext.class, executionContext);
                    }
                }, context);
                
            });
        } catch (ActivityException e) {
            saga.compensate();
            throw e;
        }
    }
    
}
