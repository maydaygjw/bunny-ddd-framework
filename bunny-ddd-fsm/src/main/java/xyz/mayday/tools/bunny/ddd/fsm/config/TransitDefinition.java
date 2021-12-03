package xyz.mayday.tools.bunny.ddd.fsm.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@With
@NoArgsConstructor
@AllArgsConstructor
public class TransitDefinition {
    
    String from;
    
    String to;
    
    String on;
    
    String whenMvel;
    
    String comment;
    
    Boolean implicit;
    
    Boolean enabled;
}
