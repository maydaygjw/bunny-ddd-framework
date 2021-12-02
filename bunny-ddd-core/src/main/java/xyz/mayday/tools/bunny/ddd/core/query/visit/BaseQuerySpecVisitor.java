package xyz.mayday.tools.bunny.ddd.core.query.visit;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import xyz.mayday.tools.bunny.ddd.core.domain.AbstractBaseDTO;
import xyz.mayday.tools.bunny.ddd.core.domain.Visitor;
import xyz.mayday.tools.bunny.ddd.schema.query.QueryComparator;
import xyz.mayday.tools.bunny.ddd.schema.query.SearchCriteria;

@Getter
public abstract class BaseQuerySpecVisitor implements Visitor<AbstractBaseDTO<?>> {

    List<SearchCriteria> searchCriteria = new ArrayList<>();

    protected Object processValue(Object value) {
        return value;
    }

    protected SearchCriteria toCriteria(QueryComparator comparator) {
        return new SearchCriteria()
                .withKey(comparator.getCompareWith())
                .withValues(comparator.getValues())
                .withSearchOperation(comparator.getSearchOperation())
                .withSearchConjunction(comparator.getSearchConjunction())
                .withConjunctionGroup(comparator.getConjunctionGroup());
    }

}
