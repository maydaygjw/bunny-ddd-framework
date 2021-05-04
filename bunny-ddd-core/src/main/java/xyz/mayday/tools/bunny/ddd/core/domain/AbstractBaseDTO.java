package xyz.mayday.tools.bunny.ddd.core.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.mayday.tools.bunny.ddd.schema.audit.OperationType;
import xyz.mayday.tools.bunny.ddd.schema.domain.BaseDomain;
import xyz.mayday.tools.bunny.ddd.schema.query.QueryComparator;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
public abstract class AbstractBaseDTO<ID> extends AbstractBaseDAO<ID> implements BaseDomain<ID> {

    Integer revision;

    String operationType;

    public void addQueryComparator(QueryComparator queryComparator) {

    }

    public void addMultiValues(String key, List<?> values) {

    }
}
