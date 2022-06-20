package xyz.mayday.tools.bunny.batch.autoconfigure;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;

import xyz.mayday.tools.bunny.batch.config.BatchTemplate;
import xyz.mayday.tools.bunny.batch.controller.BatchController;

@Import(BatchController.class)
@Configuration
@ConditionalOnBean(BatchTemplate.class)
@EntityScan("xyz.mayday.tools.bunny.batch.entity")
@EnableJpaRepositories("xyz.mayday.tools.bunny.batch.repo")
public class BatchAutoConfiguration {
    
    @Autowired
    BatchTemplate<? super Object, ? super Object> batchTemplate;
    
    @Autowired
    StepBuilderFactory stepBuilderFactory;
    
    @Bean
    Flow mainFlow() {
        return new FlowBuilder<Flow>("mainFlow").start(stepBuilderFactory.get("preDefinedTask").tasklet(batchTemplate.getPreDefinedTask()).build())
                .on("SKIPPED").end().on("*").to(masterStep()).end();
    }
    
    Step masterStep() {
        return stepBuilderFactory.get("masterStep").partitioner(slaveStep()).partitioner(slaveStep().getName(), batchTemplate.getPartitioner())
                .gridSize(batchTemplate.getConcurrency()).taskExecutor(new ConcurrentTaskExecutor()).build();
    }
    
    Step slaveStep() {
        return stepBuilderFactory.get("slaveStep").chunk(1).reader(batchTemplate.getReader()).writer(batchTemplate.getWriter())
                .processor(batchTemplate.getProcessor()).build();
    }
}
