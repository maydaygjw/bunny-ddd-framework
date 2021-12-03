package xyz.mayday.tools.bunny.ddd.schema.query;

import java.util.Collection;
import java.util.Collections;

import lombok.*;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

/** @author gejunwen */
@Data
@With
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class QueryComparator {
    
    String key;
    
    String compareWith;
    
    Collection<?> values;
    
    SearchOperation searchOperation;
    
    public QueryComparator(String key) {
        this.key = key;
    }
    
    public QueryComparator(String compareWith, Object value) {
        this(compareWith, Collections.singleton(value));
    }
    
    public QueryComparator(String compareWith, Collection<?> values) {
        this.key = compareWith;
        this.compareWith = compareWith;
        this.values = values;
    }
    
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
