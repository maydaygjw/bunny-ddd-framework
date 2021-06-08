package xyz.mayday.tools.bunny.ddd.core.domain;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import xyz.mayday.tools.bunny.ddd.schema.domain.BaseDAO;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.util.Date;

/**
 * @author gejunwen
 */
@Data
@MappedSuperclass
public abstract class AbstractBaseDAO<ID> implements BaseDAO<ID> {

    @Id
    ID id;

    @Version
    Integer version;

    Date createdDate;

    Date updatedDate;

    String createdBy;

    String updatedBy;

    @Override
    public String getDomainName() {

        String simpleName = getClass().getSimpleName();
        if(StringUtils.endsWithIgnoreCase(simpleName, "DAO")) {
            return StringUtils.substringBeforeLast(simpleName, "DAO");
        } else if(StringUtils.endsWithIgnoreCase(simpleName, "DO")) {
            return StringUtils.substringBeforeLast(simpleName, "DO");
        }
        return simpleName;
    }
}
