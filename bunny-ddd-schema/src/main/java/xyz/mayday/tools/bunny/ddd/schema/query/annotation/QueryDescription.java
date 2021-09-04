package xyz.mayday.tools.bunny.ddd.schema.query.annotation;

public @interface QueryDescription {

  SortOrder[] sortOrders() default @SortOrder(value = "updatedDate", direction = "DESC");

  QueryComparator[] queryComparators() default @QueryComparator();
}
