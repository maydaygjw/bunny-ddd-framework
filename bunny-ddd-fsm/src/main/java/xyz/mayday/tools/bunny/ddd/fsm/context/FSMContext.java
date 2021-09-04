package xyz.mayday.tools.bunny.ddd.fsm.context;

import lombok.Data;

/** @author gejunwen */
@Data
public abstract class FSMContext<T> {

  public FSMContext(String key, T payload) {
    this.key = key;
    this.payload = payload;
  }

  public FSMContext(T payload) {
    this.payload = payload;
  }

  String key;

  T payload;

  T current;

  Throwable throwable;
}
