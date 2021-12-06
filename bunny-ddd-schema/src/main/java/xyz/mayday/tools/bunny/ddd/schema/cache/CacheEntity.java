package xyz.mayday.tools.bunny.ddd.schema.cache;

public @interface CacheEntity {
    
    String schema() default "";
    
    String name();
}
