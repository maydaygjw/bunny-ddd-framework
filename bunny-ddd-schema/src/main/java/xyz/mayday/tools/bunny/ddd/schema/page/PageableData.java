package xyz.mayday.tools.bunny.ddd.schema.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@ApiModel("分页数据集")
public class PageableData<T> {

    @ApiModelProperty("数据集")
    List<T> records;

    @ApiModelProperty("分页信息")
    PageInfo pageInfo;

}
