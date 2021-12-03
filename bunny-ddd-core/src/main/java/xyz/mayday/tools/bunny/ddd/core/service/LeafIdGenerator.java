package xyz.mayday.tools.bunny.ddd.core.service;

import java.util.List;

import xyz.mayday.tools.bunny.ddd.schema.service.IdGenerator;

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
