package xyz.mayday.tools.bunny.ddd.sample.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import xyz.mayday.tools.bunny.ddd.core.domain.AbstractBaseVO;
import xyz.mayday.tools.bunny.ddd.schema.domain.BaseVO;

import javax.persistence.Id;

@EqualsAndHashCode(callSuper = true)
@Data
@With
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("待办")
public class TodoVO extends AbstractBaseVO<Long> {

    @ApiModelProperty("ID")
    Long id;

    @ApiModelProperty("姓名")
    String name;

    @ApiModelProperty("描述")
    String description;

}
