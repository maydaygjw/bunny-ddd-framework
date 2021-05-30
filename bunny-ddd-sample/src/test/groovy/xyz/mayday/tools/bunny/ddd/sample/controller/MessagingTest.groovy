package xyz.mayday.tools.bunny.ddd.sample.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification

import javax.annotation.processing.Processor

@SpringBootTest
@DirtiesContext
class MessagingTest extends Specification {

    @Autowired
    Processor pip;

    def "Test for Spring Cloud Stream"() {
        given:
            pipe
        expect:
            pip
    }
}
