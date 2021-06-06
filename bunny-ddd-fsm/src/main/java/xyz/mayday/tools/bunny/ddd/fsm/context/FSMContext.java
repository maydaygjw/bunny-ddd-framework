package xyz.mayday.tools.bunny.ddd.fsm.context;

import lombok.Data;

/**
 * @author gejunwen
 */

@Data
public abstract class FSMContext<T> {

    String key;

    T payload;

    T oldPayload;

    Throwable throwable;

}
