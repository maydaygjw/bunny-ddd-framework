package xyz.mayday.tools.bunny.ddd.context.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import xyz.mayday.tools.bunny.ddd.context.service.ServiceConfiguration;

@Configuration
@ConditionalOnProperty(value = "bunny.ddd.enabled", havingValue = "true")
@Import(ServiceConfiguration.class)
public class DDDAutoConfiguration {
}
