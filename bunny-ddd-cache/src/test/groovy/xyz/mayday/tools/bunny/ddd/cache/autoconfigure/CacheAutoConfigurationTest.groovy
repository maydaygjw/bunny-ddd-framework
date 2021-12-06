package xyz.mayday.tools.bunny.ddd.cache.autoconfigure

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import redis.embedded.RedisServer
import spock.lang.Shared
import spock.lang.Specification
import xyz.mayday.tools.bunny.ddd.cache.domain.UserDO
import xyz.mayday.tools.bunny.ddd.cache.service.UnderlyingUserService
import xyz.mayday.tools.bunny.ddd.cache.service.UserService
import xyz.mayday.tools.bunny.ddd.schema.service.BaseService

@SpringBootTest
@Configuration
class CacheAutoConfigurationTest extends Specification {

    @Autowired
    UserService userService

    @Shared
    RedisServer redisServer;

    def setup() {
        redisServer = new RedisServer();
        redisServer.start()
    }

    def "auto configuration"() {

        when:

        def user = userService.insert(new UserDO("Peter", 18))
        def queryUser = userService.findItemById(user.getId())

        then:

        user.name == "Peter"
        queryUser.get().name == "Peter"

    }

    def cleanup() {
        redisServer.stop()
    }

    @Bean
    BaseService<Long, UserDO> userUnderlyingService() {
        return new UnderlyingUserService();
    }

}
