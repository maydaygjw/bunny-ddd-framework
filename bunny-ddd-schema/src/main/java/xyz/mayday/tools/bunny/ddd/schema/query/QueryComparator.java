package xyz.mayday.tools.bunny.ddd.schema.query;

import lombok.Data;

/**
 * @author gejunwen
 */
@Data
public class QueryComparator {

    String key;

    String compareWith;

    SearchOperation operation;

    SearchConjunction conjunction;
}
