package xyz.mayday.tools.bunny.ddd.context.autoconfigure;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

import xyz.mayday.tools.bunny.ddd.context.utils.EnvUtils;

@Slf4j
public class DDDEnvironmentPostProcessor implements EnvironmentPostProcessor {
    
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        EnvUtils.loadResource(environment, "bunny.ddd.enabled", "ddd");
    }
    
}
