package xyz.mayday.tools.bunny.ddd.core.service

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation
import spock.lang.Specification
import xyz.mayday.tools.bunny.ddd.core.converter.DefaultGenericConverter
import xyz.mayday.tools.bunny.ddd.core.domain.Domain
import xyz.mayday.tools.bunny.ddd.core.domain.Domain.UserDAO
import xyz.mayday.tools.bunny.ddd.core.domain.UserService
import xyz.mayday.tools.bunny.ddd.schema.auth.PrincipalService
import xyz.mayday.tools.bunny.ddd.schema.service.PersistenceServiceFactory

@SpringBootTest
@Configuration
class AbstractBaseRDBMSServiceTest extends Specification {

    @Autowired
    UserService userService;

    def setup() {
        def mapper = new ObjectMapper()
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        userService.setConverter(new DefaultGenericConverter(mapper))
        userService.setIdGenerator(new LeafIdGenerator())
        def principalService = Stub(PrincipalService)
        principalService.currentUserId >> "1"
        principalService.currentUserName >> "Bob"

        def repo = Stub(JpaRepositoryImplementation.class);
        repo.save(_ as UserDAO) >> {UserDAO user -> user}
        def serviceFactory = Stub(PersistenceServiceFactory.class)
        serviceFactory.getRepository(_) >> repo

        userService.setPrincipalService(principalService)
        userService.setServiceFactory(serviceFactory)
    }

    def "insert"() {
        given:
            def user = new Domain.UserDTO().withUserName("Bob").withAge(18)
            user.id = 1L
        when:
            user = userService.insert(user)
        then:
            user.version == 1
    }

    def "update"() {

    }

}
