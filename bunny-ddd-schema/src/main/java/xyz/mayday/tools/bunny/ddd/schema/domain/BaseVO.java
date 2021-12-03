package xyz.mayday.tools.bunny.ddd.schema.domain;

public interface BaseVO<ID> {
    
    ID getId();
    
    Integer getVersion();
    
    Integer getRevision();
}
