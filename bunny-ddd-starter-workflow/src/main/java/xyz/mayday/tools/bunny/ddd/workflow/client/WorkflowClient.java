package xyz.mayday.tools.bunny.ddd.workflow.client;

import com.uber.cadence.worker.Worker;
import lombok.AllArgsConstructor;
import xyz.mayday.tools.bunny.ddd.workflow.execution.CommonSagaWorkflow;
import xyz.mayday.tools.bunny.ddd.workflow.execution.CompensatoryActivity;
import xyz.mayday.tools.bunny.ddd.workflow.config.WorkflowProperties;

import java.util.List;

@AllArgsConstructor
public class WorkflowClient {

    private com.uber.cadence.client.WorkflowClient workflowClient;

    private Worker worker;

    WorkflowProperties properties;

    public void registerWorkflowImplementation(Class<?> workflowImplementation) {

    }

    public void registerActivity(Class<?> activityClass) {

    }

    public void executeSaga(String taskList, List<CompensatoryActivity> activityList) {

        workflowClient.newWorkflowStub(CommonSagaWorkflow.class).executeSaga(null);
    }

}
