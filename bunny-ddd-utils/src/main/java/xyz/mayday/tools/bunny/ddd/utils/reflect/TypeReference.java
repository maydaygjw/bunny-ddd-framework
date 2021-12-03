package xyz.mayday.tools.bunny.ddd.utils.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

public abstract class TypeReference<T> {
    
    private final Type type;
    
    private volatile Constructor<?> constructor;
    
    protected TypeReference() {
        Type superClass = getClass().getGenericSuperclass();
        if (superClass instanceof Class) {
            throw new RuntimeException("Missing type parameter");
        }
        type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
    }
    
    @SuppressWarnings("unchecked")
    public T newInstance() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if (constructor == null) {
            Class<?> rawType = getRawType();
            constructor = rawType.getConstructor();
        }
        return (T) constructor.newInstance();
    }
    
    public Type getType() {
        return type;
    }
    
    public Class<?> getRawType() {
        return type instanceof Class<?> ? (Class<?>) type : (Class<?>) ((ParameterizedType) type).getRawType();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        TypeReference<?> that = (TypeReference<?>) o;
        return Objects.equals(type, that.type);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}
