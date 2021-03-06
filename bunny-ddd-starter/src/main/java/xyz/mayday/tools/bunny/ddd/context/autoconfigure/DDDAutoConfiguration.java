package xyz.mayday.tools.bunny.ddd.context.autoconfigure;

import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;

import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.GenericApplicationContext;

import xyz.mayday.tools.bunny.ddd.context.PagingProperties;
import xyz.mayday.tools.bunny.ddd.context.ResponseAdvice;
import xyz.mayday.tools.bunny.ddd.context.factory.ContextHolder;
import xyz.mayday.tools.bunny.ddd.context.service.DefaultPrincipalService;
import xyz.mayday.tools.bunny.ddd.core.converter.DefaultGenericConverter;
import xyz.mayday.tools.bunny.ddd.core.converter.StringToDateConverter;
import xyz.mayday.tools.bunny.ddd.core.service.DefaultDomainAggregator;
import xyz.mayday.tools.bunny.ddd.core.service.DefaultHistoryService;
import xyz.mayday.tools.bunny.ddd.core.service.LeafIdGenerator;
import xyz.mayday.tools.bunny.ddd.schema.auth.PrincipalService;
import xyz.mayday.tools.bunny.ddd.schema.converter.GenericConverter;
import xyz.mayday.tools.bunny.ddd.schema.page.PagingParameters;
import xyz.mayday.tools.bunny.ddd.schema.service.DomainAggregator;
import xyz.mayday.tools.bunny.ddd.schema.service.HistoryService;
import xyz.mayday.tools.bunny.ddd.schema.service.IdGenerator;
import xyz.mayday.tools.bunny.ddd.utils.jackson.ObjectMapperFactory;

@Configuration
@ConditionalOnProperty(value = "bunny.ddd.enabled", havingValue = "true")
@Import({ DocumentConfiguration.class, PagingProperties.class, ResponseAdvice.class })
@ComponentScan(basePackageClasses = ContextHolder.class)
@EnableSchedulerLock(defaultLockAtMostFor = "10m")
public class DDDAutoConfiguration {
    
    @Autowired
    GenericApplicationContext ctx;
    
    @Bean
    GenericConverter converter() {
        return new DefaultGenericConverter(ObjectMapperFactory.create());
    }
    
    @Bean
    IdGenerator<String> idGenerator() {
        return new LeafIdGenerator();
    }
    
    @Bean
    DomainAggregator domainAggregator() {
        return new DefaultDomainAggregator();
    }
    
    @Bean
    @ConditionalOnMissingBean(PrincipalService.class)
    PrincipalService principalService() {
        return new DefaultPrincipalService();
    }
    
    @Bean
    HistoryService historyService(Javers javers) {
        return new DefaultHistoryService(javers);
    }
    
    @Bean
    StringToDateConverter stringToDateConverter() {
        return new StringToDateConverter();
    }
    
    @Bean
    @ConditionalOnMissingBean(PagingParameters.class)
    PagingParameters pagingConfigure(PagingProperties pagingProperties) {
        return new PagingParameters() {
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
    
    @ConditionalOnMissingBean(Javers.class)
    @Bean
    Javers javers() {
        return JaversBuilder.javers().build();
    }
}
