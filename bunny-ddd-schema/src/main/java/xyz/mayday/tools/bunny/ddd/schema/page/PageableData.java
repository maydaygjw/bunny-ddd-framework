package xyz.mayday.tools.bunny.ddd.schema.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ApiModel("分页数据集")
public class PageableData<T> {

  @ApiModelProperty("数据集")
  List<T> records;

  @ApiModelProperty("分页信息")
  PageInfo pageInfo;
}
