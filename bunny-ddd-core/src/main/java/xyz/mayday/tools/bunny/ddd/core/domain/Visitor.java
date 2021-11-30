package xyz.mayday.tools.bunny.ddd.core.domain;

public interface Visitor<T> {

    void visit(T target);
}
