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
public class DomainEvent<T> {

    String id;

    Integer version;

    Date timestamp;

    String source;

    T domain;

    EventTypeEnum eventType;

}
