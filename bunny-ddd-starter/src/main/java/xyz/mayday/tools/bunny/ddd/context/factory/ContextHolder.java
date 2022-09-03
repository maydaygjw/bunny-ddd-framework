package xyz.mayday.tools.bunny.ddd.context.factory;

import java.util.Objects;
import java.util.Optional;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import xyz.mayday.tools.bunny.ddd.schema.converter.GenericConverter;
import xyz.mayday.tools.bunny.ddd.schema.service.DomainAggregator;
import xyz.mayday.tools.bunny.ddd.utils.reflect.TypeReference;

@Component
@Slf4j
public class ContextHolder implements ApplicationContextAware {
    
    static final String fakeAppName = "bunny-fake-app";
    
    private static ApplicationContext ctx;
    
    @Getter
    private static GenericConverter genericConverter;
    
    @Getter
    private static DomainAggregator domainAggregator;
    
    @SuppressWarnings("unchecked")
    public static <T> Optional<T> getBeanOfType(TypeReference<T> typeReference) {
        return Optional.of((T) ctx.getBeansOfType(typeReference.getRawType()).values().stream().findAny());
    }
    
    public static <T> Optional<T> getBeanOfType(Class<T> type) {
        return ctx.getBeansOfType(type).values().stream().findAny();
    }
    
    @Autowired
    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        ContextHolder.ctx = ctx;
    }
    
    @Autowired
    public void setDomainAggregator(DomainAggregator domainAggregator) {
        ContextHolder.domainAggregator = domainAggregator;
    }
    
    @Autowired
    public void setGenericConverter(GenericConverter genericConverter) {
        ContextHolder.genericConverter = genericConverter;
    }
    
    public static String getAppName() {
        if (Objects.isNull(ctx)) {
            log.warn("No actual application context found, just return a fake app name: [{}]", fakeAppName);
            return fakeAppName;
        }
        return ctx.getEnvironment().getProperty("spring.application.name");
    }
    
}
