package xyz.mayday.tools.bunny.ddd.core.query;

import org.springframework.data.jpa.domain.Specification;
import xyz.mayday.tools.bunny.ddd.schema.domain.BaseDAO;
import xyz.mayday.tools.bunny.ddd.schema.exception.BusinessException;
import xyz.mayday.tools.bunny.ddd.schema.query.SearchCriteria;
import xyz.mayday.tools.bunny.ddd.schema.query.SearchOperation;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/** @author gejunwen */
public class QuerySpecification<DAO extends BaseDAO<?>> extends QueryCondition
    implements Specification<DAO> {

  public QuerySpecification(List<SearchCriteria<?>> searchCriteriaList) {
    super(searchCriteriaList);
  }

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
