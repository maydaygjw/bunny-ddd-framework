package xyz.mayday.tools.bunny.ddd.core.query;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

/** @author gejunwen */
@Getter
@NoArgsConstructor
public class QuerySpecification<DAO extends BaseDAO<?>> extends QueryCondition implements Specification<DAO> {
    
    DAO root;
    
    AbstractBaseDTO<?> domain;
    
    @Override
    public Predicate toPredicate(Root<DAO> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        
        List<Predicate> collect = getAndCriteriaList().stream().map(searchCriteria -> {
            
            QueryPredicate predicate = QueryPredicate.presetPredicates.get(searchCriteria.getSearchOperation());
            if (Objects.isNull(predicate)) {
                throw new BusinessException();
            }
            return predicate.buildPredicate(searchCriteria, root, builder);
        }).collect(Collectors.toList());
        return query.where(collect.toArray(new Predicate[0])).getRestriction();
    }
}
