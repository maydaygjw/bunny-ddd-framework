package xyz.mayday.tools.bunny.ddd.context.autoconfigure

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification
import xyz.mayday.tools.bunny.ddd.schema.converter.GenericConverter
import xyz.mayday.tools.bunny.ddd.schema.service.IdGenerator

@SpringBootTest
class TestDDDAutoConfiguration extends Specification {

    @Autowired
    IdGenerator<String> idGenerator

    @Autowired
    GenericConverter genericConverter;

    def "auto configuration is working fine"() {
        expect:
            idGenerator.generate()
            genericConverter
    }
}
