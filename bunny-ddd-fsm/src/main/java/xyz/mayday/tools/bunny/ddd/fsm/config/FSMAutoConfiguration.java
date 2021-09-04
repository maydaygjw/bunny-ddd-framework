package xyz.mayday.tools.bunny.ddd.fsm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.mayday.tools.bunny.ddd.fsm.impl.StateMachineFactory;

@Configuration
@EnableConfigurationProperties(FSMProperties.class)
@ConditionalOnProperty(prefix = "bunny.ddd.fsm", name = "enabled", havingValue = "true")
public class FSMAutoConfiguration {

  @Autowired ApplicationContext ctx;

  @Bean
  public StateMachineFactory stateMachineFactory() {
    return null;
  }
}
