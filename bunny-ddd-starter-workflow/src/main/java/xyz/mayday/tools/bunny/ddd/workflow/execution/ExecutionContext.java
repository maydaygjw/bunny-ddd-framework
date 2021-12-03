package xyz.mayday.tools.bunny.ddd.workflow.execution;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExecutionContext<DOMAIN> {
    
    String correlationId;
    
    DOMAIN domain;
    
    Object result;
}
