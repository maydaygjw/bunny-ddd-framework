package xyz.mayday.tools.bunny.ddd.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.SneakyThrows;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.reflections.Reflections;

/** @author gejunwen */
@SuppressWarnings("unchecked")
public class ReflectionUtils extends org.reflections.ReflectionUtils {
    
    @SneakyThrows
    public static <T> T getValue(Field f, Object instance) {
        f.setAccessible(true);
        return (T) f.get(instance);
    }
    
    public static <T> T getValue(String fieldName, Object instance) {
        Field f = FieldUtils.getField(instance.getClass(), fieldName, true);
        return getValue(f, instance);
    }
    
    public static <T> Class<T> getGenericTypeOfSuperClass(Object instance, int index) {
        Type t = instance.getClass().getGenericSuperclass();
        return (Class<T>) ((ParameterizedType) t).getActualTypeArguments()[index];
    }
    
    @SneakyThrows
    public static <T> T newInstance(Class<T> cls) {
        return cls.newInstance();
    }
    
    @SneakyThrows
    public static <T> boolean hasProperty(T o, String fieldName) {
        return Arrays.stream(o.getClass().getDeclaredFields()).anyMatch(field -> field.getName().equals(fieldName));
    }
    
    public static Set<Class<?>> scanClassesByAnnotationType(List<String> basePaths, Class<? extends Annotation> annotationClass) {
        return basePaths.stream().map(basePath -> new Reflections(basePath).getTypesAnnotatedWith(annotationClass)).flatMap(Collection::stream)
                .collect(Collectors.toSet());
        
    }
}
