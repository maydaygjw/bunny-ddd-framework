package xyz.mayday.tools.bunny.ddd.schema.query;

import lombok.*;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.Objects;

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

  SearchConjunction searchConjunction = SearchConjunction.AND;

  String conjunctionGroup = "Default";

}
