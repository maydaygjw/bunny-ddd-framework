package xyz.mayday.tools.bunny.ddd.schema.page;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.NoArgsConstructor;

@Data
@Builder
@ApiModel("分页数据集")
@NoArgsConstructor
@AllArgsConstructor
public class PageableData<T> {
    
    @ApiModelProperty("数据集")
    List<T> records;
    
    @ApiModelProperty("分页信息")
    PageInfo pageInfo;
}
