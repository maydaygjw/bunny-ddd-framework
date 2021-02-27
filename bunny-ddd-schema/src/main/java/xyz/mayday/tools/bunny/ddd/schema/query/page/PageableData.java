package xyz.mayday.tools.bunny.ddd.schema.query.page;

import lombok.Data;

import java.util.List;

@Data
public class PageableData<T> {

    List<T> records;

    PageInfo pageInfo;

}
