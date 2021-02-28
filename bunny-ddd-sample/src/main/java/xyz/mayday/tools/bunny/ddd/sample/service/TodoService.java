package xyz.mayday.tools.bunny.ddd.sample.service;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import xyz.mayday.tools.bunny.ddd.core.service.AbstractBaseRDBMSService;
import xyz.mayday.tools.bunny.ddd.sample.domain.TodoDO;
import xyz.mayday.tools.bunny.ddd.sample.domain.TodoTagDO;

import javax.persistence.criteria.*;
import java.util.List;

/**
 * @author gejunwen
 */
@Service
public class TodoService extends AbstractBaseRDBMSService<Long, TodoDO, TodoDO> {

    public List<TodoDO> findWithAuthorization(TodoDO todoDO) {

        return getRepository().findAll(new Specification<TodoDO>() {
            @Override
            public Predicate toPredicate(Root<TodoDO> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

                Subquery<TodoTagDO> subquery = query.subquery(TodoTagDO.class);
                Root<TodoTagDO> todoTagRoot = subquery.from(TodoTagDO.class);
                subquery.select(todoTagRoot.get("todoId"));
                subquery.where(builder.equal(todoTagRoot.get("tagName"), "Men"));

                return builder.in(root.get("id")).value(subquery);
            }
        });
    }
}
