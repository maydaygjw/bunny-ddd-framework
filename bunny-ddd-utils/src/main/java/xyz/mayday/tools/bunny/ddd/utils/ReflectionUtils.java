package xyz.mayday.tools.bunny.ddd.utils;

import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author gejunwen
 */

@SuppressWarnings("unchecked")
public class ReflectionUtils {

    @SneakyThrows
    public static <T> T getValue(Field f, Object instance) {
        f.setAccessible(true);
        return (T) f.get(instance);
    }

    public static <T> Class<T> getGenericTypeOfSuperClass(Object instance, int index) {
        Type t = instance.getClass().getGenericSuperclass();
        return (Class<T>)((ParameterizedType)t).getActualTypeArguments()[index];
    }

    @SneakyThrows
    public static <T> T newInstance(Class<T> cls) {
        return cls.newInstance();
    }

}
