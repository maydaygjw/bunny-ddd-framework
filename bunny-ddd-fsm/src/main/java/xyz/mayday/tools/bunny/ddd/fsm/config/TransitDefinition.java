package xyz.mayday.tools.bunny.ddd.fsm.config;

import lombok.Data;

@Data
public class TransitDefinition {

    String from;

    String to;

    String on;

    String whenMvel;

    String comment;

    Boolean implicit;

    Boolean enabled;
}
