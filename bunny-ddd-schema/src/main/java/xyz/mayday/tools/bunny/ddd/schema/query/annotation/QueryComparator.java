package xyz.mayday.tools.bunny.ddd.schema.query.annotation;

import xyz.mayday.tools.bunny.ddd.schema.query.SearchConjunction;
import xyz.mayday.tools.bunny.ddd.schema.query.SearchOperation;

public @interface QueryComparator {

  String key() default "";

  String[] compareWith() default "";

  SearchOperation operation() default SearchOperation.EQUALS;

  SearchConjunction conjunction() default SearchConjunction.AND;

  SearchConjunction.ConjunctionGroup conjunctionGroup() default
      SearchConjunction.ConjunctionGroup.DEFAULT;
}
