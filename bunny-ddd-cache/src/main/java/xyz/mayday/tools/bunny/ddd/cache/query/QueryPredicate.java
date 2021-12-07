package xyz.mayday.tools.bunny.ddd.cache.query;

import java.util.Map;

import xyz.mayday.tools.bunny.ddd.schema.query.SearchOperation;

import com.google.common.collect.ImmutableMap;

public abstract class QueryPredicate {
    
    SearchOperation searchOperation;
    
    public static final Map<SearchOperation, QueryPredicate> presetPredicates = ImmutableMap.<SearchOperation, QueryPredicate> builder()
            .put(SearchOperation.EQUAL, new EqualPredicate()).put(SearchOperation.MATCH, new MatchPredicate()).put(SearchOperation.IN, new InPredicate())
            .build();
    
    public QueryPredicate(SearchOperation searchOperation) {
        this.searchOperation = searchOperation;
    }
    
    public abstract String buildPattern(String value);
    
    private static class EqualPredicate extends QueryPredicate {
        
        public EqualPredicate() {
            super(SearchOperation.EQUAL);
        }
        
        @Override
        public String buildPattern(String value) {
            return value;
        }
    }
    
    private static class MatchPredicate extends QueryPredicate {
        
        public MatchPredicate() {
            super(SearchOperation.MATCH);
        }
        
        @Override
        public String buildPattern(String value) {
            return "*" + value + "*";
        }
    }
    
    private static class InPredicate extends QueryPredicate {
        
        public InPredicate() {
            super(SearchOperation.IN);
        }
        
        @Override
        public String buildPattern(String value) {
            return value;
        }
    }
}
