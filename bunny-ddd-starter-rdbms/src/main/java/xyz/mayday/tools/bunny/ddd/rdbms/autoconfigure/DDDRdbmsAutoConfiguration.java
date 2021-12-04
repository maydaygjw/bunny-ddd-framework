package xyz.mayday.tools.bunny.ddd.rdbms.autoconfigure;

import javax.persistence.EntityManager;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import xyz.mayday.tools.bunny.ddd.rdbms.factory.ApplicationContextServiceFactory;
import xyz.mayday.tools.bunny.ddd.schema.service.PersistenceServiceFactory;

@Configuration
public class DDDRdbmsAutoConfiguration {
    
    @Bean
    PersistenceServiceFactory serviceFactory(EntityManager entityManager, ApplicationContext ctx, JdbcTemplate jdbcTemplate) {
        return new ApplicationContextServiceFactory(entityManager, ctx, jdbcTemplate);
    }
}
