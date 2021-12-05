package xyz.mayday.tools.bunny.ddd.schema.domain;

public interface BaseVO<ID> {
    
    ID getId();
    
    Integer getVersion();
    
    Long getRevision();
    
    void setId(ID id);
}
