package xyz.mayday.tools.bunny.ddd.context.factory;

import java.util.Optional;

import lombok.Getter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import xyz.mayday.tools.bunny.ddd.schema.converter.GenericConverter;
import xyz.mayday.tools.bunny.ddd.schema.service.DomainAggregator;
import xyz.mayday.tools.bunny.ddd.utils.reflect.TypeReference;

@Component
public class ContextHolder implements ApplicationContextAware {
    
    private static ApplicationContext ctx;
    
    @Getter
    private static String appName;
    
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
    
    @Autowired
    public void setAppName(@Value("${application.name}") String appName) {
        ContextHolder.appName = appName;
    }
    
}
