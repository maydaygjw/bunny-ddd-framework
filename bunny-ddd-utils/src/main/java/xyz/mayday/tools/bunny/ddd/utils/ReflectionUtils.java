package xyz.mayday.tools.bunny.ddd.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author gejunwen
 */

@SuppressWarnings("unchecked")
public class ReflectionUtils {

    public static <T> T getValue(Field f, Object instance) {
        try {
            f.setAccessible(true);
            return (T) f.get(instance);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> Class<T> getGenericTypeOfSuperClass(Object instance, int index) {
        Type t = instance.getClass().getGenericSuperclass();
        return (Class<T>)((ParameterizedType)t).getActualTypeArguments()[index];
    }

    public static <T> T newInstance(Class<T> cls) {
        try {
            return cls.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
