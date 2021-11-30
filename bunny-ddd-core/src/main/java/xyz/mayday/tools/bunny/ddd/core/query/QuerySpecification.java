package xyz.mayday.tools.bunny.ddd.core.query;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import xyz.mayday.tools.bunny.ddd.core.domain.AbstractBaseDTO;
import xyz.mayday.tools.bunny.ddd.schema.domain.BaseDAO;
import xyz.mayday.tools.bunny.ddd.schema.exception.BusinessException;
import xyz.mayday.tools.bunny.ddd.schema.query.SearchOperation;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/** @author gejunwen */
@Getter
@NoArgsConstructor
public class QuerySpecification<DAO extends BaseDAO<?>> extends QueryCondition
    implements Specification<DAO> {

    DAO root;

    AbstractBaseDTO<?> domain;

  @Override
  public Predicate toPredicate(Root<DAO> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

    return query
        .where(
            getSearchCriteriaList().stream()
                .map(
                    searchCriteria -> {
                      if (searchCriteria.getSearchOperation().equals(SearchOperation.EQUALS)) {
                        return builder.equal(
                            root.get(searchCriteria.getKey()), searchCriteria.getValue());
                      } else if (searchCriteria
                          .getSearchOperation()
                          .equals(SearchOperation.MATCH)) {
                        return builder.like(
                            root.get(searchCriteria.getKey()),
                            "%" + searchCriteria.getValue() + "%");
                      } else {
                        throw new BusinessException();
                      }
                    })
                .toArray(Predicate[]::new))
        .getRestriction();
  }
}
