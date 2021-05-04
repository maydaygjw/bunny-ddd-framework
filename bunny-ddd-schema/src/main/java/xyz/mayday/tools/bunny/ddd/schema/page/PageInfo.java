package xyz.mayday.tools.bunny.ddd.schema.page;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

@Data
@Builder
public class PageInfo {

    Integer totalPages;

    Integer currentPage;

    Long totalRecords;

    public static PageInfo fromPage(Page<?> page) {
        return PageInfo.builder().totalRecords(page.getTotalElements()).totalPages(page.getTotalPages()).build();
    }
}
