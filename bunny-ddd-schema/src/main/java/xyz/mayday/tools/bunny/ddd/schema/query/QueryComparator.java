package xyz.mayday.tools.bunny.ddd.schema.query;

import java.util.Collection;

import lombok.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

/** @author gejunwen */
@Data
@With
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class QueryComparator<T> {

  String key;

  String compareWith;

  Collection<T> values;

  SearchOperation searchOperation;

  SearchConjunction searchConjunction;

  String conjunctionGroup;

  public SearchOperation getSearchOperation() {
    return ObjectUtils.defaultIfNull(searchOperation, SearchOperation.EQUALS);
  }

  public SearchConjunction getSearchConjunction() {
    return ObjectUtils.defaultIfNull(searchConjunction, SearchConjunction.AND);
  }

  public String getConjunctionGroup() {
    return StringUtils.defaultIfBlank(conjunctionGroup, "Default");
  }
}
