package xyz.mayday.tools.bunny.ddd.core.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import xyz.mayday.tools.bunny.ddd.schema.query.SearchCriteria;

import java.util.List;

@Data
@AllArgsConstructor
public class QueryCondition {

    List<SearchCriteria<?>> searchCriteriaList;
}
