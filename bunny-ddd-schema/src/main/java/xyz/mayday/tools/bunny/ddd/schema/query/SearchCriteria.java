package xyz.mayday.tools.bunny.ddd.schema.query;

import lombok.Data;

import java.util.List;

@Data
public class SearchCriteria<T> {

    List<String> keys;

    List<T> values;

    SearchOperation searchOperation;
}
