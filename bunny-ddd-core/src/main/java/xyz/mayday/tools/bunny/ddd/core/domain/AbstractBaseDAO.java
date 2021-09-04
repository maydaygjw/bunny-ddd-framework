package xyz.mayday.tools.bunny.ddd.core.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.javers.core.metamodel.annotation.DiffIgnore;
import xyz.mayday.tools.bunny.ddd.schema.domain.BaseDAO;

/**
 * @author gejunwen
 */
@Data
@MappedSuperclass
public abstract class AbstractBaseDAO<ID extends Serializable> implements BaseDAO<ID> {

    private static final String DAO_SUFFIX = "DAO";
    private static final String DO_SUFFIX = "DO";

    @Id
    ID id;

    @DiffIgnore
    @Version
    Integer version;

    @DiffIgnore
    Date createdDate;

    @DiffIgnore
    Date updatedDate;

    @DiffIgnore
    String createdBy;

    @DiffIgnore
    String updatedBy;

    @Override
    public String getDomainName() {

        String simpleName = getClass().getSimpleName();
        if(StringUtils.endsWithIgnoreCase(simpleName, DAO_SUFFIX)) {
            return StringUtils.substringBeforeLast(simpleName, DAO_SUFFIX);
        } else if(StringUtils.endsWithIgnoreCase(simpleName, DO_SUFFIX)) {
            return StringUtils.substringBeforeLast(simpleName, DO_SUFFIX);
        }
        return simpleName;
    }
}
