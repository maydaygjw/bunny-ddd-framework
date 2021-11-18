package xyz.mayday.tools.bunny.ddd.workflow.execution;

public interface CompensatoryActivity {

    void invoke(ExecutionContext context);

    void Compensate(ExecutionContext context);
}
