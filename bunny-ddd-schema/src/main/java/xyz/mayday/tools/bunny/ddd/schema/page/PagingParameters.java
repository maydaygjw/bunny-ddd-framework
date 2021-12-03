package xyz.mayday.tools.bunny.ddd.schema.page;

public interface PagingParameters {
    
    Integer getDefaultPageSize();
    
    Integer getPageSizeLimit();
}
