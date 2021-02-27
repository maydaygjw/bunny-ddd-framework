package xyz.mayday.tools.bunny.ddd.schema.query.page;

import lombok.Data;

@Data
public class PageInfo {

    Integer totalPages;

    Integer currentPage;

    Long totalRecords;
}
