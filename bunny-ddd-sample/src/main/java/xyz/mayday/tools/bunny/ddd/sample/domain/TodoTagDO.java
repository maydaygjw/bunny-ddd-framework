package xyz.mayday.tools.bunny.ddd.sample.domain;

import lombok.*;
import xyz.mayday.tools.bunny.ddd.core.domain.AbstractBaseDAO;
import xyz.mayday.tools.bunny.ddd.core.domain.AbstractBaseDTO;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author gejunwen
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@With
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_todo_tag")
public class TodoTagDO extends AbstractBaseDTO<String> {

    String tagName;

    Long todoId;
}
