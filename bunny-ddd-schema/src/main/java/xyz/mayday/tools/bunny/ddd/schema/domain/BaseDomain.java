package xyz.mayday.tools.bunny.ddd.schema.domain;


import xyz.mayday.tools.bunny.ddd.schema.query.QueryComparator;

import java.util.List;

public interface BaseDomain<ID> {

    void addQueryComparator(QueryComparator queryComparator);

    void addMultiValues(String key, List<?> values);

    ID getId();
}
