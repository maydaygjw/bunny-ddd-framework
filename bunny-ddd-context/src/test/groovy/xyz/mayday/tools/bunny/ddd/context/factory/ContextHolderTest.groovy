package xyz.mayday.tools.bunny.ddd.context.factory

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import spock.lang.Specification
import xyz.mayday.tools.bunny.ddd.context.autoconfigure.Domain
import xyz.mayday.tools.bunny.ddd.utils.reflect.TypeReference

@SpringBootTest
@Configuration
class ContextHolderTest extends Specification {

    def "GetBeanOfType"() {

        when:
        def type = ContextHolder.getBeanOfType(TypedInterface.class)
        def type2 = ContextHolder.getBeanOfType(new TypeReference<TypedInterface<List<String>>>() {})
        def type3 = ContextHolder.getBeanOfType(new TypeReference<TypedInterface<Set<String>>>() {})
        then:
        type.present
        type2.present
        type3.present
    }

    def "GetGenericConverter"() {
        when:

        def converter = ContextHolder.getGenericConverter()
        def anotherUser = converter.clone(new Domain.UserDTO().withUserName("Peter"))

        then:

        anotherUser.userName == "Peter"

    }

    @Bean
    TypedInterface<List<String>> typedInterface() {
        return new TypedImpl();
    }


}

class TypedImpl implements TypedInterface<List<String>> {


    @Override
    String sayType() {
        getClass().getGenericInterfaces()[0].typeName
    }
}

interface TypedInterface<T> {

    String sayType();

}