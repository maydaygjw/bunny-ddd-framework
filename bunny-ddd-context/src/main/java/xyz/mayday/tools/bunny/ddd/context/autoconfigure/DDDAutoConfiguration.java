package xyz.mayday.tools.bunny.ddd.context.autoconfigure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import xyz.mayday.tools.bunny.ddd.context.PagingProperties;
import xyz.mayday.tools.bunny.ddd.context.factory.ApplicationContextServiceFactory;
import xyz.mayday.tools.bunny.ddd.core.converter.DefaultGenericConverter;
import xyz.mayday.tools.bunny.ddd.core.service.LeafIdGenerator;
import xyz.mayday.tools.bunny.ddd.schema.converter.GenericConverter;
import xyz.mayday.tools.bunny.ddd.schema.page.PagingConfigure;
import xyz.mayday.tools.bunny.ddd.schema.service.IdGenerator;
import xyz.mayday.tools.bunny.ddd.schema.service.PersistenceServiceFactory;

import javax.persistence.EntityManager;

@Configuration
@ConditionalOnProperty(value = "bunny.ddd.enabled", havingValue = "true")
@Import(PagingProperties.class)
public class DDDAutoConfiguration {

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

    @Bean
    @ConditionalOnMissingBean(PagingConfigure.class)
    PagingConfigure pagingConfigure(PagingProperties pagingProperties) {
        return new PagingConfigure() {
            @Override
            public Integer getDefaultPageSize() {
                return pagingProperties.getPageSizeDefault();
            }

            @Override
            public Integer getPageSizeLimit() {
                return pagingProperties.getPageSizeLimit();
            }
        };
    }
}
