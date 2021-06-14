package xyz.mayday.tools.bunny.ddd.core.converter

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification

class DefaultGenericConverterTest extends Specification {

    def "basic conversion"() {
        given:
            def mapper = new ObjectMapper()
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            def converter = new DefaultGenericConverter(mapper)
            def userVO = new UserVO("1", "Bill", 1, 1)
            userVO.subs = [new UserSubVO("Gates")]
        when:
            def query = converter.convert(userVO, UserQuery.class)
        then:
            query.name == "Bill"
            query.subs[0].name == "Gates"
    }

    static class UserVO {
        String id
        String name
        Integer version
        Integer revision

        List<UserSubVO> subs;

        UserVO(String id, String name, Integer version, Integer revision) {
            this.id = id
            this.name = name
            this.version = version
            this.revision = revision
        }
    }

    static class UserSubVO {
        String name;

        UserSubVO(String name) {
            this.name = name
        }
    }

    static class UserQuery {
        String name
        Long id
        String desc
        List<UserQuerySubQuery> subs;
    }

    static class UserQuerySubQuery {
        String name;
    }
}
