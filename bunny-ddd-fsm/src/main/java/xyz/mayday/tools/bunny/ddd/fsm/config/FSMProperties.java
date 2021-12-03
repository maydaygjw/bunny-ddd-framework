package xyz.mayday.tools.bunny.ddd.fsm.config;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "bunny.ddd.fsm")
@Data
public class FSMProperties {
    
    String enabled;
    
    String basePackages;
}
