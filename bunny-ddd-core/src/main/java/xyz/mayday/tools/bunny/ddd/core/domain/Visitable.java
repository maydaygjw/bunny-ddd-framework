package xyz.mayday.tools.bunny.ddd.core.domain;

public interface Visitable {

    void accept(Visitor<?> visitor);

}
