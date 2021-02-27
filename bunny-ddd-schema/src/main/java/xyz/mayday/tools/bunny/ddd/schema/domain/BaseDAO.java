package xyz.mayday.tools.bunny.ddd.schema.domain;

import java.util.Date;

public interface BaseDAO<ID> {

    ID getId();

    Integer getVersion();

    Date getCreatedDate();

    Date getUpdatedDate();

    String getCreatedBy();

    String getUpdatedBy();
}
