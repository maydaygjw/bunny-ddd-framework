package xyz.mayday.tools.bunny.ddd.cache.domain

import groovy.transform.TupleConstructor
import xyz.mayday.tools.bunny.ddd.core.domain.AbstractBaseDTO

@TupleConstructor
class UserDO extends AbstractBaseDTO<Long>{

    String name;

    Integer age;
}
