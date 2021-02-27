package xyz.mayday.tools.bunny.ddd.core.query;

import org.springframework.data.jpa.domain.Specification;
import xyz.mayday.tools.bunny.ddd.schema.domain.BaseDAO;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author gejunwen
 */
public class QuerySpecification extends QueryCondition implements Specification<BaseDAO<?>> {
    @Override
    public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return null;
    }
}
