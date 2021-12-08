package xyz.mayday.tools.bunny.ddd.cache.query;

import java.util.Date;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.apache.commons.text.StringSubstitutor;

import xyz.mayday.tools.bunny.ddd.context.factory.ContextHolder;
import xyz.mayday.tools.bunny.ddd.schema.exception.BusinessException;

import com.google.common.collect.ImmutableMap;

public class RedisUtils {
    
    public static String getCacheItemDataKeyWildcard(String cacheName) {
        String keyTemplate = "_data:${app.name}:${cache.name}:*:object";
        return StringSubstitutor.replace(keyTemplate, ImmutableMap.of("app.name", ContextHolder.getAppName(), "cache.name", cacheName));
    }
    
    public static String getCacheItemIndexKeyWildcard(String cacheName) {
        return getCacheItemIndexKey("*", "*", cacheName);
    }
    
    public static String getCacheItemDataKey(String id, String cacheName) {
        String keyTemplate = "_data:${app.name}:${cache.name}:${data.id}:object";
        return StringSubstitutor.replace(keyTemplate, ImmutableMap.of("app.name", ContextHolder.getAppName(), "cache.name", cacheName, "data.id", id));
    }
    
    public static String getCacheItemIndexKey(String property, String value, String cacheName) {
        String indexKeyTemplate = "_idx:${app.name}:${cache.name}:${prop.name}:${prop.value}:set";
        return StringSubstitutor.replace(indexKeyTemplate,
                ImmutableMap.of("app.name", ContextHolder.getAppName(), "cache.name", cacheName, "prop.name", property, "prop.value", value));
        
    }
    
    public static String getCacheItemIndexKeyOfZSet(String property, String cacheName) {
        String indexKeyTemplate = "_idx:${app.name}:${cache.name}:${prop.name}:zset";
        return StringSubstitutor.replace(indexKeyTemplate,
                ImmutableMap.of("app.name", ContextHolder.getAppName(), "cache.name", cacheName, "prop.name", property));
    }
    
    public static Double calculateScore(Object value) {
        if (NumberUtils.isCreatable(value.toString())) {
            Number number = NumberUtils.createNumber(value.toString());
            return number.doubleValue();
        } else if (TypeUtils.isInstance(value, Date.class)) {
            return new Long(((Date) value).getTime()).doubleValue();
        } else {
            throw new BusinessException();
        }
    }
}
