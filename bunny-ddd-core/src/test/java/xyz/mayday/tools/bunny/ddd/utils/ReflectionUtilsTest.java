package xyz.mayday.tools.bunny.ddd.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ReflectionUtilsTest {
    
    @Test
    void getValue() {
    }
    
    @Test
    void getGenericTypeOfSuperClass() {
        
        Assertions.assertEquals(String.class, ReflectionUtils.getGenericTypeOfSuperClass(new Child(), 0));
        Assertions.assertEquals(Integer.class, ReflectionUtils.getGenericTypeOfSuperClass(new Child(), 1));
    }
    
    static class Parent<P, K> {
    }
    
    static class Child extends Parent<String, Integer> {
    }
}
