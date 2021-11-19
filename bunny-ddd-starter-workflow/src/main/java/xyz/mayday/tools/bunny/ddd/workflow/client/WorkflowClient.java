package xyz.mayday.tools.bunny.ddd.workflow.client;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.List;

import com.uber.cadence.client.WorkflowOptions;
import com.uber.cadence.client.WorkflowStub;
import com.uber.cadence.internal.common.InternalUtils;
import com.uber.cadence.worker.Worker;

import com.uber.cadence.workflow.WorkflowMethod;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.reflect.MethodUtils;
import xyz.mayday.tools.bunny.ddd.workflow.config.WorkflowProperties;
import xyz.mayday.tools.bunny.ddd.workflow.execution.CommonSagaWorkflow;
import xyz.mayday.tools.bunny.ddd.workflow.execution.SimpleWorkflow;

@AllArgsConstructor
public class WorkflowClient {

    private com.uber.cadence.client.WorkflowClient workflowClient;

    private Worker worker;

    WorkflowProperties properties;

    public void executeSaga(String taskList, List<Class<?>> activitySpecList) {

        workflowClient.newWorkflowStub(CommonSagaWorkflow.class).executeSaga(activitySpecList, null);
    }

    public Object executeActivity(Class<?> activitySpec, Object... args) {
        return workflowClient.newWorkflowStub(SimpleWorkflow.class).invokeActivity(activitySpec, args).getResult();
    }

    public void executeWorkflow(Class<?> workflowSpec, Object... args) {
        Method workflowMethod = InternalUtils.getWorkflowMethod(workflowSpec);
        String workflowType = InternalUtils.getSimpleName(workflowMethod);
        WorkflowStub workflowStub = workflowClient.newUntypedWorkflowStub(workflowType, new WorkflowOptions.Builder()
                .setExecutionStartToCloseTimeout(Duration.ofSeconds(30))
                .setTaskList("test-task")
                .build());
        workflowStub.start(args);
    }


}
