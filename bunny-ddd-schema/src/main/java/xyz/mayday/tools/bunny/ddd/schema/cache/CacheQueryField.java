package xyz.mayday.tools.bunny.ddd.schema.cache;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { ElementType.FIELD })
public @interface CacheQueryField {
}
