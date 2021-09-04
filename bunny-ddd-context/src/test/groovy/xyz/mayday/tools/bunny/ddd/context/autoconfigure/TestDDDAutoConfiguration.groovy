package xyz.mayday.tools.bunny.ddd.context.autoconfigure


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import spock.lang.Shared
import spock.lang.Specification
import xyz.mayday.tools.bunny.ddd.core.service.AbstractBaseRDBMSService
import xyz.mayday.tools.bunny.ddd.schema.auth.PrincipalService
import xyz.mayday.tools.bunny.ddd.schema.converter.GenericConverter
import xyz.mayday.tools.bunny.ddd.schema.exception.BusinessException
import xyz.mayday.tools.bunny.ddd.schema.service.IdGenerator
import xyz.mayday.tools.bunny.ddd.schema.service.PersistenceServiceFactory

@SpringBootTest
@Configuration
class TestDDDAutoConfiguration extends Specification {

    @Autowired
    IdGenerator<String> idGenerator

    @Autowired
    GenericConverter genericConverter;

    @Autowired
    UserService userService;

    @Shared
    Domain.UserDTO user;

    def "auto configuration and injection is working fine"() {
        expect:
        idGenerator.generate()
        genericConverter
        userService
    }

    def "basic CRUD of insert"() {
        given:
        user = new Domain.UserDTO()
        user.with { userName = "Bob"; age = 20 }
        when:
        user = userService.insert(user)
        def found = userService.findItemById(user.id)
        then:
        user.id
        user.version == 1
        user.createdBy == "MockId"
        found.present

    }

    def "basic CRUD of update"() {
        given:
        user.age = 21
        when:
        def idBackup = user.id
        user.id = null
        user = userService.update(user)
        then:
        thrown(BusinessException.class)
        when:
        user.id = idBackup
        user = userService.update(user)
        def found = userService.findItemById(user.id).get()
        then:
        found.age == 21
        found.version == 2
        user.updatedBy == "MockId"

    }

    def "basic CRUD of delete"() {
        given:
        def NON_EXIST_ID = 123789L
        when:
        userService.delete(NON_EXIST_ID)
        then:
        thrown(BusinessException.class)
        when:
        userService.delete(user.id)
        def found = userService.findItemById(user.id)
        then:
        !found.isPresent()
    }

    def "basic CRUD of conditional query"() {
        given:
        user = new Domain.UserDTO()
        user.with { userName = "Bob"; age = 20 }
        userService.insert(user)
        when:
        def all = userService.findAll(new Domain.UserDTO().withAge(20))
        then:
        all.size() == 1
    }


    static class UserService extends AbstractBaseRDBMSService<Long, Domain.UserDTO, Domain.UserDAO> {
        UserService(GenericConverter converter, PrincipalService principalService, PersistenceServiceFactory serviceFactory, IdGenerator<String> idGenerator, GenericConverter genericConverter) {
            super(converter, principalService, serviceFactory, idGenerator, genericConverter)
        }
    }

    @Bean
    UserService userServiceBean(GenericConverter converter, PersistenceServiceFactory serviceFactory, IdGenerator<String> idGenerator, GenericConverter genericConverter) {

        def principalService = Stub(PrincipalService)
        principalService.getCurrentUserId() >> "MockId"

        return new UserService(converter, principalService, serviceFactory, idGenerator, genericConverter)
    }

}
