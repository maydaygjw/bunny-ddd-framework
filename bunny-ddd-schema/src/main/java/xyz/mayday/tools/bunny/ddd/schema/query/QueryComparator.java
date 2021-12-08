package xyz.mayday.tools.bunny.ddd.schema.query;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
    
    List<String> compareWith;
    
    Collection<?> values;
    
    SearchOperation searchOperation;
    
    public QueryComparator(String key) {
        this.key = key;
    }
    
    public QueryComparator(String key, SearchOperation searchOperation) {
        this.key = key;
        this.compareWith = Collections.singletonList(key);
        this.searchOperation = searchOperation;
    }
    
    public QueryComparator(String compareWith, Object value) {
        this(Collections.singletonList(compareWith), Collections.singleton(value));
    }
    
    public QueryComparator(List<String> compareWith, Collection<?> values) {
        this.key = compareWith.get(0);
        this.compareWith = compareWith;
        this.values = values;
    }
    
    SearchConjunction searchConjunction;
    
    String conjunctionGroup;
    
    public SearchOperation getSearchOperation() {
        return ObjectUtils.defaultIfNull(searchOperation, SearchOperation.EQUAL);
    }
    
    public SearchConjunction getSearchConjunction() {
        return ObjectUtils.defaultIfNull(searchConjunction, SearchConjunction.AND);
    }
    
    public String getConjunctionGroup() {
        return StringUtils.defaultIfBlank(conjunctionGroup, "Default");
    }
}
