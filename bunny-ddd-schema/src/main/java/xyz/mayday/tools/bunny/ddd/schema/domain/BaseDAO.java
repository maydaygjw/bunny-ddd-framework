package xyz.mayday.tools.bunny.ddd.schema.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;

public interface BaseDAO<ID> {

  ID getId();

  Integer getVersion();

  Date getCreatedDate();

  Date getUpdatedDate();

  String getCreatedBy();

  String getUpdatedBy();

  void setId(ID id);

  void setVersion(Integer version);

  void setCreatedBy(String createdBy);

  void setUpdatedBy(String updatedBy);

  void setCreatedDate(Date createdDate);

  void setUpdatedDate(Date updatedDate);

  @JsonIgnore
  String getDomainName();
}
