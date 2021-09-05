package xyz.mayday.tools.bunny.ddd.core.query;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import xyz.mayday.tools.bunny.ddd.schema.query.SearchCriteria;

@Data
@AllArgsConstructor
public class QueryCondition {

  List<SearchCriteria<?>> searchCriteriaList;
}
