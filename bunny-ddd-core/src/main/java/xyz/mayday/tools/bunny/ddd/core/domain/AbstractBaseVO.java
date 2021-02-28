package xyz.mayday.tools.bunny.ddd.core.domain;

import lombok.Data;
import xyz.mayday.tools.bunny.ddd.schema.audit.OperationType;
import xyz.mayday.tools.bunny.ddd.schema.domain.BaseVO;

/**
 * @author gejunwen
 */

@Data
public abstract class AbstractBaseVO<ID> implements BaseVO<ID> {

    ID id;

    Integer version;

    Integer revision;

    String operationType;
}
