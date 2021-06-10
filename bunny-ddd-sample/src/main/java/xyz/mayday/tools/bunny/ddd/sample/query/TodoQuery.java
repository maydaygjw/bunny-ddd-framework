package xyz.mayday.tools.bunny.ddd.sample.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("待办查询")
@Data
public class TodoQuery {

    @ApiModelProperty("姓名")
    String name;

    @ApiModelProperty("描述")
    String description;
}
