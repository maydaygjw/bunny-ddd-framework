package xyz.mayday.tools.bunny.ddd.schema.query.annotation;

public @interface SortOrder {
    
    String value();
    
    String direction() default "DESC";
}
