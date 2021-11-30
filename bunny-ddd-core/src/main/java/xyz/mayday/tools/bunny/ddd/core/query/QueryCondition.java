package xyz.mayday.tools.bunny.ddd.core.query;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.mayday.tools.bunny.ddd.schema.query.SearchCriteria;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryCondition {

  List<SearchCriteria> searchCriteriaList = new ArrayList<>();

  public void addAll(List<SearchCriteria> searchCriteriaList) {
    this.searchCriteriaList.addAll(searchCriteriaList);
  }
}
