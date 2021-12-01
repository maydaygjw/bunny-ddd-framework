package xyz.mayday.tools.bunny.ddd.core.query.visit;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import xyz.mayday.tools.bunny.ddd.core.domain.AbstractBaseDTO;
import xyz.mayday.tools.bunny.ddd.core.domain.Visitor;
import xyz.mayday.tools.bunny.ddd.schema.query.SearchCriteria;

@Getter
public abstract class BaseQuerySpecVisitor implements Visitor<AbstractBaseDTO<?>> {

    List<SearchCriteria> querySpecifications = new ArrayList<>();

    protected Object processValue(Object value) {
        return value;
    }

}
