package xyz.mayday.tools.bunny.ddd.schema.query;

public enum SearchConjunction {
    AND,
    OR,;
    
    public enum ConjunctionGroup {
        DEFAULT,
        FIRST,
        SECOND,;
    }
}
