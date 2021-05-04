package xyz.mayday.tools.bunny.ddd.sample.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class TodoControllerTest extends Specification {

    @Autowired
    TodoController todoController;

    def "when context is loaded then all expected beans are created"() {
        expect: "the TodoController is created"
        todoController
    }
}
