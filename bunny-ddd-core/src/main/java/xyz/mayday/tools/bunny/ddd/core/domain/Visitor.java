package xyz.mayday.tools.bunny.ddd.core.domain;

public interface Visitor {

    void visit(AbstractBaseDTO<?> dto);
}
