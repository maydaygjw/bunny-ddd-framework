package xyz.mayday.tools.bunny.ddd.schema.event;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@With
@NoArgsConstructor
@AllArgsConstructor
public class DomainEvent<DOMAIN> {
    
    String eventId;
    
    Integer version;
    
    Date publishTime;
    
    String source;
    
    DOMAIN data;
    
    DOMAIN lastVersion;
    
    EventTypeEnum eventType;
}
