package xyz.mayday.tools.bunny.ddd.workflow.client;

import java.util.List;

import com.uber.cadence.worker.Worker;

import lombok.AllArgsConstructor;
import xyz.mayday.tools.bunny.ddd.workflow.config.WorkflowProperties;
import xyz.mayday.tools.bunny.ddd.workflow.execution.CommonSagaWorkflow;

@AllArgsConstructor
public class WorkflowClient {

    private com.uber.cadence.client.WorkflowClient workflowClient;

    private Worker worker;

    WorkflowProperties properties;

    public void registerWorkflowImplementation(Class<?> workflowImplementation) {

    }

    public void registerActivityImplementation(Class<?> activityClass) {
        worker.registerActivitiesImplementations(activityClass);
    }

    public void executeSaga(String taskList, List<Class<?>> activitySpecList) {

        workflowClient.newWorkflowStub(CommonSagaWorkflow.class).executeSaga(activitySpecList, null);
    }


}
