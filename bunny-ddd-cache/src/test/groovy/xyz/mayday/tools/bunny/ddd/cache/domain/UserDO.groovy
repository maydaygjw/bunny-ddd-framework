package xyz.mayday.tools.bunny.ddd.cache.domain

import groovy.transform.TupleConstructor
import xyz.mayday.tools.bunny.ddd.core.domain.AbstractBaseDTO
import xyz.mayday.tools.bunny.ddd.schema.cache.CacheQueryField

@TupleConstructor
class UserDO extends AbstractBaseDTO<Long>{

    @CacheQueryField
    String name;

    @CacheQueryField
    Integer age;
}
