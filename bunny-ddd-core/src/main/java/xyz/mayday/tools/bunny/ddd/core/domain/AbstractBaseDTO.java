package xyz.mayday.tools.bunny.ddd.core.domain;

import java.io.Serializable;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.mayday.tools.bunny.ddd.schema.domain.BaseDomain;
import xyz.mayday.tools.bunny.ddd.schema.query.QueryComparator;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class AbstractBaseDTO<ID extends Serializable> extends AbstractBaseDAO<ID>
    implements BaseDomain<ID> {

  Integer revision;

  String operationType;

  public void addQueryComparator(QueryComparator queryComparator) {}

  public void addMultiValues(String key, List<?> values) {}
}
