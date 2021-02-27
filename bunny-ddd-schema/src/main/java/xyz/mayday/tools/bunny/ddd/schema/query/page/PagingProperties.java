package xyz.mayday.tools.bunny.ddd.schema.query.page;

import lombok.Data;

@Data
public class PagingProperties {

    Integer maxPageSize;

    Integer internalMaxPageSize;

    Integer defaultPageSize;
}
