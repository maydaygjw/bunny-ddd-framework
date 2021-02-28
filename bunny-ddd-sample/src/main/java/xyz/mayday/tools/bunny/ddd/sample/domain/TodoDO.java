package xyz.mayday.tools.bunny.ddd.sample.domain;

import lombok.*;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditOverrides;
import org.hibernate.envers.Audited;
import xyz.mayday.tools.bunny.ddd.core.domain.AbstractBaseDAO;
import xyz.mayday.tools.bunny.ddd.core.domain.AbstractBaseDTO;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@With
@AllArgsConstructor
@ToString
@Data
@Entity
@Table(name = "t_todo")
@Audited
@AuditOverride(forClass = AbstractBaseDAO.class)
public class TodoDO extends AbstractBaseDTO<Long> {

    String name;

    String description;

}
