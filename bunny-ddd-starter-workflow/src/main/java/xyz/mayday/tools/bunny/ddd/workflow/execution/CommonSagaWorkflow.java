package xyz.mayday.tools.bunny.ddd.workflow.execution;

import com.uber.cadence.workflow.WorkflowMethod;

public interface CommonSagaWorkflow {

    @WorkflowMethod(executionStartToCloseTimeoutSeconds = 5, taskList = "test-task")
    void executeSaga(ExecutionContext executionContext);
}
