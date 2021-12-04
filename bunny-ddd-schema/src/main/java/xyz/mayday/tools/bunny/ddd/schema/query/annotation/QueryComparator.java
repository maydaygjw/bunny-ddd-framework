package xyz.mayday.tools.bunny.ddd.schema.query.annotation;

import java.lang.annotation.*;

import xyz.mayday.tools.bunny.ddd.schema.query.SearchConjunction;
import xyz.mayday.tools.bunny.ddd.schema.query.SearchOperation;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.FIELD })
public @interface QueryComparator {
    
    String key() default "";
    
    String[] compareWith() default "";
    
    SearchOperation operation() default SearchOperation.EQUAL;
    
    SearchConjunction conjunction() default SearchConjunction.AND;
    
    String conjunctionGroup() default "Default";
}
