package xyz.mayday.tools.bunny.ddd.core.query;

import lombok.Data;
import xyz.mayday.tools.bunny.ddd.schema.query.SearchCriteria;

import java.util.List;

@Data
public class QueryCondition {

    List<SearchCriteria<?>> searchCriteriaList;
}
