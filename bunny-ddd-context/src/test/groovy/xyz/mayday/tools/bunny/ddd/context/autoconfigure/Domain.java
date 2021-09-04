package xyz.mayday.tools.bunny.ddd.context.autoconfigure;

import lombok.*;
import org.hibernate.envers.Audited;
import xyz.mayday.tools.bunny.ddd.core.domain.AbstractBaseDAO;
import xyz.mayday.tools.bunny.ddd.core.domain.AbstractBaseDTO;
import xyz.mayday.tools.bunny.ddd.schema.view.AbstractBaseVO;

import javax.persistence.Entity;

public class Domain {

  @EqualsAndHashCode(callSuper = true)
  @Data
  @With
  @AllArgsConstructor
  @NoArgsConstructor
  public static class UserDTO extends AbstractBaseDTO<Long> {
    String userName;
    Integer age;
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
