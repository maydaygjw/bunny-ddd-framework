package xyz.mayday.tools.bunny.ddd.sample.vo;

import lombok.*;
import xyz.mayday.tools.bunny.ddd.core.domain.AbstractBaseVO;
import xyz.mayday.tools.bunny.ddd.schema.domain.BaseVO;

@EqualsAndHashCode(callSuper = true)
@Data
@With
@AllArgsConstructor
@NoArgsConstructor
public class TodoVO extends AbstractBaseVO<Long> {

    Long id;

    String name;

    String description;

}
