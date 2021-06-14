package xyz.mayday.tools.bunny.ddd.schema.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Streams;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@With
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("通用查询参数")
public class CommonQueryParam {

    @ApiModelProperty("当前页数")
    Integer currentPage;

    @ApiModelProperty("每页记录数")
    Integer pageSize;

    @ApiModelProperty("排序字段")
    List<String> sortField = new ArrayList<>();

    @ApiModelProperty(value = "排序方向", allowableValues = "ASC,DESC")
    List<String> sortOrder = new ArrayList<>();

    @JsonIgnore
    public List<Sort.Order> collectSortOrders() {
        return Streams.zip(sortField.stream(), sortOrder.stream(), Pair::of).map(pair -> new Sort.Order(Sort.Direction.fromString(pair.getSecond()), pair.getFirst())).collect(Collectors.toList());
    }

}
