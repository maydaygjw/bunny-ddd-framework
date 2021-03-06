package xyz.mayday.tools.bunny.ddd.schema.query;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;
import org.springframework.util.CollectionUtils;

import xyz.mayday.tools.bunny.ddd.schema.domain.DataStateEnum;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Streams;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Data
@With
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("通用查询参数")
public class CommonQueryParam {
    
    public static final int DEFAULT_PAGE_SIZE = 20;
    
    @ApiModelProperty("当前页数")
    int currentPage;
    
    @ApiModelProperty("每页记录数")
    int pageSize;
    
    @ApiModelProperty("排序字段")
    List<String> sortField = new ArrayList<>();
    
    @ApiModelProperty(value = "排序方向", allowableValues = "ASC,DESC")
    List<String> sortOrder = new ArrayList<>();
    
    @ApiModelProperty("数据状态（默认值查询有效的数据）")
    List<DataStateEnum> dataState = new ArrayList<>();
    
    public Integer getCurrentPage() {
        return currentPage > 0 ? currentPage : 1;
    }
    
    @JsonIgnore
    public List<Sort.Order> collectSortOrders() {
        return Streams.zip(sortField.stream(), sortOrder.stream(), Pair::of)
                .map(pair -> new Sort.Order(Sort.Direction.fromString(pair.getSecond()), pair.getFirst())).collect(Collectors.toList());
    }
    
    public List<DataStateEnum> getDataState() {
        return CollectionUtils.isEmpty(dataState) ? Collections.singletonList(DataStateEnum.VALID) : dataState;
    }
}
