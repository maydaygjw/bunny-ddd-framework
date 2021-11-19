package xyz.mayday.tools.bunny.ddd.workflow.autoconfig;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.uber.cadence.client.WorkflowClientOptions;
import com.uber.cadence.serviceclient.ClientOptions;
import com.uber.cadence.serviceclient.WorkflowServiceTChannel;
import com.uber.cadence.worker.Worker;
import com.uber.cadence.worker.WorkerFactory;

import xyz.mayday.tools.bunny.ddd.utils.ReflectionUtils;
import xyz.mayday.tools.bunny.ddd.workflow.activity.ActivityImplementation;
import xyz.mayday.tools.bunny.ddd.workflow.client.WorkflowClient;
import xyz.mayday.tools.bunny.ddd.workflow.config.WorkflowProperties;
import xyz.mayday.tools.bunny.ddd.workflow.execution.CommonSagaWorkflowImpl;
import xyz.mayday.tools.bunny.ddd.workflow.execution.SimpleWorkflow;
import xyz.mayday.tools.bunny.ddd.workflow.flow.WorkflowImplementation;

@Configuration
@ConditionalOnProperty(prefix = "bunny.ddd.workflow", name = "enabled", havingValue = "true")
@EnableConfigurationProperties(WorkflowProperties.class)
public class WorkflowAutoConfiguration {

    @Autowired
    ApplicationContext applicationContext;

    @Bean
    WorkflowClient workflowClient(WorkflowProperties workflowProperties) {
        com.uber.cadence.client.WorkflowClient workflowClient =
                com.uber.cadence.client.WorkflowClient.newInstance(
                        new WorkflowServiceTChannel(ClientOptions.defaultInstance()),
                        WorkflowClientOptions.newBuilder().setDomain(workflowProperties.getDomain()).build());

        WorkerFactory workerFactory = WorkerFactory.newInstance(workflowClient);
        Worker worker = workerFactory.newWorker(workflowProperties.getTaskList());

        List<String> packagesToScan = AutoConfigurationPackages.get(applicationContext);
        Set<Class<?>> workflowClasses = ReflectionUtils.scanClassesByAnnotationType(packagesToScan, WorkflowImplementation.class);

        //register workflow
        workflowClasses.add(CommonSagaWorkflowImpl.class);
        workflowClasses.add(SimpleWorkflow.SimpleWorkFlowImpl.class);
        worker.registerWorkflowImplementationTypes(workflowClasses.toArray(new Class[0]));

        //register activity implementations
        Map<String, ?> beansOfType = applicationContext.getBeansWithAnnotation(ActivityImplementation.class);
        worker.registerActivitiesImplementations(beansOfType.values().toArray());

        workerFactory.start();

        return new WorkflowClient(workflowClient, worker, workflowProperties);
    }
}
