package xyz.mayday.tools.bunny.ddd.schema.query;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum SearchOperation {

    EQUALS,
    MATCH,
    GREATER_THAN,
    GREATER_THAN_EQUAL
    ;
}
