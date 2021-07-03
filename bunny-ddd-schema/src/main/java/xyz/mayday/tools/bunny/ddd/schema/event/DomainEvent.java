package xyz.mayday.tools.bunny.ddd.schema.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

import java.util.Date;

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
