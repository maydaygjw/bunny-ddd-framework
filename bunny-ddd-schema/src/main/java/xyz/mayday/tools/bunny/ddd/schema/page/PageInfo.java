package xyz.mayday.tools.bunny.ddd.schema.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
@Builder
@ApiModel("分页信息")
public class PageInfo {

    @ApiModelProperty("总页数")
    Integer totalPages;

    @ApiModelProperty("当前页")
    Integer currentPage;

    @ApiModelProperty("总记录数")
    Long totalRecords;

    public static PageInfo fromPage(Page<?> page) {
        return PageInfo.builder().totalRecords(page.getTotalElements()).totalPages(page.getTotalPages()).build();
    }
}
