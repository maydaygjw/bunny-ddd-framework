package xyz.mayday.tools.bunny.ddd.schema.page;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PageableData<T> {

    List<T> records;

    PageInfo pageInfo;

}
