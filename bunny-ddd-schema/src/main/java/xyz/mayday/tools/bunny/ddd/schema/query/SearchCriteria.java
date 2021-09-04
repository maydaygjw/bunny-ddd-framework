package xyz.mayday.tools.bunny.ddd.schema.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteria<T> {

  List<String> keys;

  List<T> values;

  SearchOperation searchOperation;

  SearchConjunction searchConjunction;

  public SearchCriteria(List<String> keys, List<T> values) {
    this.keys = keys;
    this.values = values;
    this.searchOperation = SearchOperation.EQUALS;
    this.searchConjunction = SearchConjunction.AND;
  }

  public String getKey() {
    return keys.get(0);
  }

  public T getValue() {
    return values.get(0);
  }
}
