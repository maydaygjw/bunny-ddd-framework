package xyz.mayday.tools.bunny.ddd.schema.auth;

import java.util.List;

public interface ActionSupport<S> {
    
    S getState();
    
    List<Action> getActions();
}
