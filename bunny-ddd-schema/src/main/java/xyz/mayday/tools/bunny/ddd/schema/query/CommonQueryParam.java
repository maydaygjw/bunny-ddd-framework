package xyz.mayday.tools.bunny.ddd.schema.query;

import lombok.Data;

import java.util.List;

@Data
public class CommonQueryParam {

    Integer currentPage;

    Integer pageSize;

    List<String> sortField;

    List<String> sortOrder;

}
