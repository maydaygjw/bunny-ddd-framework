package xyz.mayday.tools.bunny.ddd.utils.functional;

@FunctionalInterface
public interface TriConsumer<K, V, S> {
    
    void accept(K k, V v, S s);
}
