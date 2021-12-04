package xyz.mayday.tools.bunny.ddd.rdbms.domain;

import lombok.*;
import xyz.mayday.tools.bunny.ddd.core.domain.AbstractBaseDAO;
import xyz.mayday.tools.bunny.ddd.core.domain.AbstractBaseDTO;
import xyz.mayday.tools.bunny.ddd.schema.view.AbstractBaseVO;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.List;

public class Domain {

  @EqualsAndHashCode(callSuper = true)
  @Data
  @With
  @AllArgsConstructor
  @NoArgsConstructor
  public static class UserDTO extends AbstractBaseDTO<Long> {
    String userName;
    Integer age;

    @Transient
    Integer userNo;

    List<String> authorizations;
  }

  @Entity
  @EqualsAndHashCode(callSuper = true)
  @Data
  @With
  @AllArgsConstructor
  @NoArgsConstructor
  public static class UserDAO extends AbstractBaseDAO<Long> {
    String userName;
    Integer age;
  }

  @Data
  @With
  @AllArgsConstructor
  @NoArgsConstructor
  public static class UserQuery {
    String userName;
  }

  @EqualsAndHashCode(callSuper = true)
  @Data
  @With
  @AllArgsConstructor
  @NoArgsConstructor
  public static class UserVO extends AbstractBaseVO<Long> {
    String userName;
    Integer age;
  }
}
