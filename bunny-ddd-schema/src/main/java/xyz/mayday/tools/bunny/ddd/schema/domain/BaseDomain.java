package xyz.mayday.tools.bunny.ddd.schema.domain;

public interface BaseDomain<ID> {
    ID getId();
    
    String getGlobalId();
    
    String getCreatedBy();
}
