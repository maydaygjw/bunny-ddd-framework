package xyz.mayday.tools.bunny.ddd.core.service;

import xyz.mayday.tools.bunny.ddd.schema.service.IdGenerator;

import java.util.List;

/** @author gejunwen */
public class LeafIdGenerator implements IdGenerator<String> {

  @Override
  public String generate() {
    return String.valueOf(System.currentTimeMillis());
  }

  @Override
  public List<String> bulkGenerate(int size) {
    return null;
  }
}
