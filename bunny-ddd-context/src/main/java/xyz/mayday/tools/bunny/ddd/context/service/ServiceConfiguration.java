package xyz.mayday.tools.bunny.ddd.context.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import xyz.mayday.tools.bunny.ddd.core.converter.DefaultGenericConverter;
import xyz.mayday.tools.bunny.ddd.core.service.LeafIdGenerator;
import xyz.mayday.tools.bunny.ddd.schema.converter.GenericConverter;
import xyz.mayday.tools.bunny.ddd.schema.service.IdGenerator;
import xyz.mayday.tools.bunny.ddd.schema.service.PersistenceServiceFactory;
import xyz.mayday.tools.bunny.ddd.schema.service.ServiceFactory;

import javax.persistence.EntityManager;

public class ServiceConfiguration {

    @Bean
    GenericConverter converter(ObjectMapper mapper) {
        return new DefaultGenericConverter(mapper);
    }

    @Bean
    IdGenerator<String> idGenerator() {
        return new LeafIdGenerator();
    }

    @Bean
    PersistenceServiceFactory serviceFactory(EntityManager entityManager, ApplicationContext ctx, JdbcTemplate jdbcTemplate) {
        return new ApplicationContextServiceFactory(entityManager, ctx, jdbcTemplate);
    }
}
