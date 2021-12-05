package xyz.mayday.tools.bunny.ddd.schema.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@AllArgsConstructor
@NoArgsConstructor
@With
public class AuditInfo {
    
    Long revision;
    
    Integer version;
    
    List<String> changedProperties;
    
    String operationType;
}
