package xyz.mayday.tools.bunny.ddd.context.factory;

import java.util.Optional;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import org.springframework.stereotype.Component;
import xyz.mayday.tools.bunny.ddd.utils.reflect.TypeReference;

@Component
public class ContextHolder implements ApplicationContextAware {

    private static ApplicationContext ctx;

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
}
