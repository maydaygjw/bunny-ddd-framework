package xyz.mayday.tools.bunny.ddd.core.query;

import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.google.common.collect.ImmutableMap;

import xyz.mayday.tools.bunny.ddd.schema.query.SearchCriteria;
import xyz.mayday.tools.bunny.ddd.schema.query.SearchOperation;

public abstract class QueryPredicate {

    public static final Map<SearchOperation, QueryPredicate> presetPredicates;

    static {
        presetPredicates = ImmutableMap.<SearchOperation, QueryPredicate>builder()
                .put(SearchOperation.EQUALS, new EqualPredicate())
                .put(SearchOperation.IN, new InPredicate())
                .put(SearchOperation.MATCH, new MatchPredicate())
                .put(SearchOperation.GREATER_THAN_EQUAL, new GreaterThanEqualsPredicate())
                .put(SearchOperation.LESS_THAN_EQUAL, new LessThanEqualsPredicate())
                .build();
    }

    SearchOperation searchOperation;

    public QueryPredicate(SearchOperation searchOperation) {
        this.searchOperation = searchOperation;
    }

    abstract Predicate buildPredicate(SearchCriteria criteria, Root<?> root, CriteriaBuilder builder);

    private static class EqualPredicate extends QueryPredicate {

        public EqualPredicate() {
            super(SearchOperation.EQUALS);
        }

        @Override
        Predicate buildPredicate(SearchCriteria criteria, Root<?> root, CriteriaBuilder builder) {
            return builder.equal(root.get(criteria.getKey()), criteria.getValue());
        }
    }

    private static class InPredicate extends QueryPredicate {

        public InPredicate() {
            super(SearchOperation.IN);
        }

        @Override
        Predicate buildPredicate(SearchCriteria criteria, Root<?> root, CriteriaBuilder builder) {
            CriteriaBuilder.In<Object> in = builder.in(root.get(criteria.getKey()));
            criteria.getValues().forEach(in::value);
            return in;
        }
    }

    private static class MatchPredicate extends QueryPredicate {

        public MatchPredicate() {
            super(SearchOperation.MATCH);
        }

        @Override
        Predicate buildPredicate(SearchCriteria criteria, Root<?> root, CriteriaBuilder builder) {
            return builder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
        }
    }

    private static class GreaterThanEqualsPredicate extends QueryPredicate {

        public GreaterThanEqualsPredicate() {
            super(SearchOperation.GREATER_THAN_EQUAL);
        }


        @Override
        Predicate buildPredicate(SearchCriteria criteria, Root<?> root, CriteriaBuilder builder) {
            return builder.greaterThanOrEqualTo(root.get(criteria.getKey()), (Comparable) criteria.getValue());
        }
    }

    private static class LessThanEqualsPredicate extends QueryPredicate {

        public LessThanEqualsPredicate() {
            super(SearchOperation.LESS_THAN_EQUAL);
        }


        @Override
        Predicate buildPredicate(SearchCriteria criteria, Root<?> root, CriteriaBuilder builder) {
            return builder.lessThanOrEqualTo(root.get(criteria.getKey()), (Comparable) criteria.getValue());
        }
    }

}
