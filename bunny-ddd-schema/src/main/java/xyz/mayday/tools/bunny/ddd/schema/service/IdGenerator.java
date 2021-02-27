package xyz.mayday.tools.bunny.ddd.schema.service;

import java.util.List;

public interface IdGenerator<ID> {

    ID generate();

    List<ID> bulkGenerate(int size);
}
