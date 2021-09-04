package xyz.mayday.tools.bunny.ddd.schema.service;

/** @author gejunwen */
public interface DistributedLock {

  void lock(String lockKey);
}
