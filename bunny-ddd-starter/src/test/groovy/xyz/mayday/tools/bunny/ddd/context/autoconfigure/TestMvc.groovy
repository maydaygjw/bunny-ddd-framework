package xyz.mayday.tools.bunny.ddd.context.autoconfigure

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.bind.annotation.RestController
import spock.lang.Specification
import xyz.mayday.tools.bunny.ddd.core.controller.BaseControllerImpl
import xyz.mayday.tools.bunny.ddd.schema.service.BaseService

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestMvc extends Specification {

    @Autowired
    UserController userController

    def "test for startup"() {
        expect:
        userController
    }

    @RestController
    static class UserController extends BaseControllerImpl<Long, Domain.UserVO, Domain.UserQuery, Domain.UserDTO> {

        @Override
        BaseService<Long, Domain.UserDTO> getService() {
            return null
        }
    }
}

