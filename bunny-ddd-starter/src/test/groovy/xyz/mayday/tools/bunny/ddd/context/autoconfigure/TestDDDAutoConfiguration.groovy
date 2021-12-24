package xyz.mayday.tools.bunny.ddd.context.autoconfigure

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class TestDDDAutoConfiguration extends Specification{

    @Value('${my.encrypt.prop}')
    String prop

    def "test for get the encrypt value"() {
        expect:
        prop == "aaa"
    }


}
