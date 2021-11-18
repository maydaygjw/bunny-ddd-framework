package xyz.mayday.tools.bunny.ddd.workflow.execution;

public class CommonSagaWorkflowImpl implements CommonSagaWorkflow{

    @Override
    public void executeSaga(ExecutionContext executionContext) {
        System.out.println("Saga workflow is executing");
    }
}
