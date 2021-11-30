package xyz.mayday.tools.bunny.ddd.core.domain;

import lombok.*;
import org.hibernate.envers.Audited;
import xyz.mayday.tools.bunny.ddd.schema.view.AbstractBaseVO;

import javax.persistence.Entity;
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

    List<String> authorizations;
  }

  @Entity
  @EqualsAndHashCode(callSuper = true)
  @Data
  @With
  @AllArgsConstructor
  @NoArgsConstructor
  @Audited
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
