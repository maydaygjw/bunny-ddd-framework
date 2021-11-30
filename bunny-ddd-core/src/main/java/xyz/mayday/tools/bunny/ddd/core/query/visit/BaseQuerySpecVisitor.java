package xyz.mayday.tools.bunny.ddd.core.query.visit;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import xyz.mayday.tools.bunny.ddd.core.domain.Visitor;
import xyz.mayday.tools.bunny.ddd.schema.query.SearchCriteria;

@Getter
public abstract class BaseQuerySpecVisitor implements Visitor {

    List<SearchCriteria> querySpecifications = new ArrayList<>();

}
