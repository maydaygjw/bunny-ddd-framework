package xyz.mayday.tools.bunny.ddd.utils


import spock.lang.Specification

class ReflectionUtilsTest extends Specification {

  def "getGenericTypeOfSuperClass"() {

        expect: String.class == ReflectionUtils.getGenericTypeOfSuperClass(new Child(), 0);
        and: Integer.class == ReflectionUtils.getGenericTypeOfSuperClass(new Child(), 1);
  }

  static class Parent<P, K> {}

  static class Child extends Parent<String, Integer> {}
}
