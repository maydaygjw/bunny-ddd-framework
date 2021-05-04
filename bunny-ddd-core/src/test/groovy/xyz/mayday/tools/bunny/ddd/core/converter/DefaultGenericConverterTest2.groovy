package xyz.mayday.tools.bunny.ddd.core.converter

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification

class DefaultGenericConverterTest2 extends Specification {

    def "basic conversion"() {
        given:
            def mapper = new ObjectMapper()
            mapper.configure DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false
            def converter = new DefaultGenericConverter(mapper)
            def userVO = new UserVO("1", "Bill", 1, 1)
        when:
            def query = converter.convert userVO, UserQuery.class
        then:
            query.name == "Bill"
    }

    static class UserVO {
        String id
        String name
        Integer version
        Integer revision

        UserVO(String id, String name, Integer version, Integer revision) {
            this.id = id
            this.name = name
            this.version = version
            this.revision = revision
        }
    }

    static class UserQuery {
        String name
        Long id
        String desc
    }
}
