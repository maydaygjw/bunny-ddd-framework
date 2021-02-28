package xyz.mayday.tools.bunny.ddd.schema.query;

import lombok.Data;

@Data
public class QueryComparator {

    String compareWith;

    SearchOperation operation;

    SearchConjunction conjunction;
}
