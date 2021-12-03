package xyz.mayday.tools.bunny.ddd.workflow.config;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("bunny.ddd.workflow")
@Data
public class WorkflowProperties {
    
    String host;
    
    String port;
    
    String domain;
    
    String taskList;
}
