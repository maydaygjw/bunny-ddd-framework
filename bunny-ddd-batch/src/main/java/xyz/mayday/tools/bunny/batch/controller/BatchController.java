package xyz.mayday.tools.bunny.batch.controller;

import com.google.common.collect.ImmutableMap;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.mayday.tools.bunny.batch.config.BatchTemplate;
import xyz.mayday.tools.bunny.batch.vo.JobExecutionVO;
import xyz.mayday.tools.bunny.ddd.schema.http.Response;

import java.util.Date;

/**
 * @author gejunwen
 */
@RestController
public class BatchController {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    JobExplorer jobExplorer;

    @Autowired
    JobRepository jobRepository;

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    BatchTemplate batchTemplate;

    @Autowired
    Flow mainFlow;

    @GetMapping("_queryJob")
    Response<JobExecutionVO> queryJob(Date initialTime) {
        JobExecution jobExecution = jobRepository.getLastJobExecution(batchTemplate.getJobName(), new JobParameters(ImmutableMap.of("initialTime", new JobParameter(initialTime))));
        return Response.success(new JobExecutionVO().withJobName(jobExecution.getJobInstance().getJobName()).withBatchStatus(jobExecution.getStatus().name()));
    }

    @PostMapping("_startJob")
    Response<Void> startJob(String jobName, Date initialTime) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        jobLauncher.run(jobBuilderFactory.get(batchTemplate.getJobName()).start(mainFlow).end().build(), new JobParameters(ImmutableMap.of("initialTime", new JobParameter(initialTime))));
        return Response.success();
    }

}
