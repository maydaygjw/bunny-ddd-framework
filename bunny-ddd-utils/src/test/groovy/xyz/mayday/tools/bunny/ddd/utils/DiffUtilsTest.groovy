package xyz.mayday.tools.bunny.ddd.utils


import spock.lang.Specification
import xyz.mayday.tools.bunny.ddd.utils.DiffUtils

class DiffUtilsTest extends Specification {

    def "test for getValueChanges"() {
        given:
            def person1 = new Person(1, "Bob")
            def person2 = new Person(1, "Harie")
        when:
            def changes = DiffUtils.getValueChanges(person1, person2)
        then:
            changes.size() == 1
            changes.get(0).getPropertyName() == "name"
            changes.get(0).left == "Bob"
            changes.get(0).right == "Harie"
    }

    static class Person {
        Integer id
        String name

        Person(Integer id, String name) {
            this.id = id
            this.name = name
        }
    }

}
