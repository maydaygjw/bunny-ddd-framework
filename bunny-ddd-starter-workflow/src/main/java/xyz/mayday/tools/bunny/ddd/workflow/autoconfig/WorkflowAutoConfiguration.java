package xyz.mayday.tools.bunny.ddd.workflow.autoconfig;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.uber.cadence.client.WorkflowClientOptions;
import com.uber.cadence.serviceclient.ClientOptions;
import com.uber.cadence.serviceclient.WorkflowServiceTChannel;
import com.uber.cadence.worker.Worker;
import com.uber.cadence.worker.WorkerFactory;

import xyz.mayday.tools.bunny.ddd.workflow.client.WorkflowClient;
import xyz.mayday.tools.bunny.ddd.workflow.config.WorkflowProperties;
import xyz.mayday.tools.bunny.ddd.workflow.execution.CommonSagaWorkflowImpl;

@Configuration
@ConditionalOnProperty(prefix = "bunny.ddd.workflow", name = "enabled", havingValue = "true")
@EnableConfigurationProperties(WorkflowProperties.class)
public class WorkflowAutoConfiguration {


    @Bean
    WorkflowClient workflowClient(WorkflowProperties workflowProperties) {
        com.uber.cadence.client.WorkflowClient workflowClient =
                com.uber.cadence.client.WorkflowClient.newInstance(
                        new WorkflowServiceTChannel(ClientOptions.defaultInstance()),
                        WorkflowClientOptions.newBuilder().setDomain(workflowProperties.getDomain()).build());

        WorkerFactory workerFactory = WorkerFactory.newInstance(workflowClient);
        Worker worker = workerFactory.newWorker(workflowProperties.getTaskList());

        worker.registerWorkflowImplementationTypes(CommonSagaWorkflowImpl.class);

        workerFactory.start();

        return new WorkflowClient(workflowClient, worker, workflowProperties);
    }
}
