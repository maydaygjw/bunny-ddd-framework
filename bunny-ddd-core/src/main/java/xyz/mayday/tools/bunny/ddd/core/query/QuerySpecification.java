package xyz.mayday.tools.bunny.ddd.core.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import lombok.Getter;
import lombok.NoArgsConstructor;
import xyz.mayday.tools.bunny.ddd.core.domain.AbstractBaseDTO;
import xyz.mayday.tools.bunny.ddd.schema.domain.BaseDAO;
import xyz.mayday.tools.bunny.ddd.schema.exception.BusinessException;
import xyz.mayday.tools.bunny.ddd.schema.query.SearchOperation;

import java.util.List;
import java.util.stream.Collectors;

/** @author gejunwen */
@Getter
@NoArgsConstructor
public class QuerySpecification<DAO extends BaseDAO<?>> extends QueryCondition implements Specification<DAO> {
    
    DAO root;
    
    AbstractBaseDTO<?> domain;
    
    @Override
    public Predicate toPredicate(Root<DAO> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        List<Predicate> collect = getSearchCriteriaList().stream().map(searchCriteria -> {
            if (searchCriteria.getSearchOperation().equals(SearchOperation.EQUALS)) {
                return builder.equal(root.get(searchCriteria.getKey()), searchCriteria.getValue());
            } else if (searchCriteria.getSearchOperation().equals(SearchOperation.MATCH)) {
                return builder.like(root.get(searchCriteria.getKey()), "%" + searchCriteria.getValue() + "%");
            } else if (searchCriteria.getSearchOperation().equals(SearchOperation.IN)) {
                CriteriaBuilder.In<Object> in = builder.in(root.get(searchCriteria.getKey()));
                searchCriteria.getValues().forEach(in::value);
                return in;
            } else if (searchCriteria.getSearchOperation().equals(SearchOperation.GREATER_THAN_EQUAL)) {
                return (Predicate) builder.greaterThanOrEqualTo(root.get(searchCriteria.getKey()), (Comparable) searchCriteria.getValue());
            } else if (searchCriteria.getSearchOperation().equals(SearchOperation.LESS_THAN_EQUAL)) {
                return (Predicate) builder.lessThanOrEqualTo(root.get(searchCriteria.getKey()), (Comparable) searchCriteria.getValue());
            } else {
                throw new BusinessException();
            }
        }).collect(Collectors.toList());
        return query.where(collect.toArray(new Predicate[0])).getRestriction();
    }
}
