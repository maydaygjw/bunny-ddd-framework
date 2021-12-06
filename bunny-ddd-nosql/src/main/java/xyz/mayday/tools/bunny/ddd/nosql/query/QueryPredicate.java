package xyz.mayday.tools.bunny.ddd.nosql.query;

import java.util.Map;

import lombok.RequiredArgsConstructor;

import org.springframework.data.mongodb.core.query.Criteria;

import xyz.mayday.tools.bunny.ddd.schema.query.SearchCriteria;
import xyz.mayday.tools.bunny.ddd.schema.query.SearchOperation;

import com.google.common.collect.ImmutableMap;

@RequiredArgsConstructor
public abstract class QueryPredicate {
    
    public static final Map<SearchOperation, QueryPredicate> presetPredicates;
    
    static {
        presetPredicates = ImmutableMap.<SearchOperation, QueryPredicate> builder().put(SearchOperation.EQUAL, new EqualPredicate()).build();
    }
    
    final SearchOperation searchOperation;
    
    public abstract Criteria buildCriteria(SearchCriteria searchCriteria);
    
    private static class EqualPredicate extends QueryPredicate {
        
        public EqualPredicate() {
            super(SearchOperation.EQUAL);
        }
        
        @Override
        public Criteria buildCriteria(SearchCriteria searchCriteria) {
            return Criteria.where(searchCriteria.getKey()).in(searchCriteria.getValues());
        }
    }
}
