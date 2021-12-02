package xyz.mayday.tools.bunny.ddd.schema.query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import lombok.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
@With
@EqualsAndHashCode
public class SearchCriteria {

  String key;

  Collection<?> values;

  SearchOperation searchOperation;

  SearchConjunction searchConjunction;

  String conjunctionGroup;

  public SearchOperation getSearchOperation() {
    return ObjectUtils.defaultIfNull(searchOperation, SearchOperation.EQUALS);
  }

  public String getConjunctionGroup() {
    return StringUtils.defaultIfBlank(conjunctionGroup, "Default");
  }

  public Object getValue() {
    if(CollectionUtils.isEmpty(values)) {
      return null;
    }
    return values.iterator().next();
  }
}
