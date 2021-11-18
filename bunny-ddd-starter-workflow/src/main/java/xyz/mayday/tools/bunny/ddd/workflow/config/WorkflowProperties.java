package xyz.mayday.tools.bunny.ddd.workflow.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties("bunny.ddd.workflow")
@Data
public class WorkflowProperties {

    String host;

    String port;

    String domain;

    String taskList;
}
