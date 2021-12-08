package xyz.mayday.tools.bunny.ddd.cache.autoconfigure

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Configuration
import redis.embedded.RedisServer
import spock.lang.Shared
import spock.lang.Specification
import xyz.mayday.tools.bunny.ddd.cache.domain.UserDO
import xyz.mayday.tools.bunny.ddd.cache.service.UserService
import xyz.mayday.tools.bunny.ddd.schema.exception.BusinessException
import xyz.mayday.tools.bunny.ddd.schema.query.QueryComparator
import xyz.mayday.tools.bunny.ddd.schema.query.SearchOperation

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

        def user = userService.insert(new UserDO("Peter"))
        def queryUser = userService.findItemById(user.getId())

        then:

        thrown BusinessException

        when:

        user = userService.insert(new UserDO("Peter", 18))

        then:

        user.id != null

//        then:
//
//        userService.findAll(new UserDO("Peter")).size() == 1
//
//        when:
//
//        def query = new UserDO("Pe")
//        query.addQueryComparator(new QueryComparator("name", SearchOperation.MATCH))
//
//        then:
//
//        userService.findAll(query).size() == 1

    }

    def cleanup() {
        redisServer.stop()
    }


}
