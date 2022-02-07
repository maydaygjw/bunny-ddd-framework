package xyz.mayday.tools.bunny.ddd.cache.autoconfigure

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.GenericContainer
import org.testcontainers.spock.Testcontainers
import org.testcontainers.utility.DockerImageName
import spock.lang.Specification
import xyz.mayday.tools.bunny.ddd.cache.domain.UserDO
import xyz.mayday.tools.bunny.ddd.cache.service.UserService
import xyz.mayday.tools.bunny.ddd.schema.exception.BusinessException
import xyz.mayday.tools.bunny.ddd.schema.query.QueryComparator
import xyz.mayday.tools.bunny.ddd.schema.query.SearchOperation

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class CacheAutoConfigurationTest extends Specification {

    static redis = new GenericContainer<>(DockerImageName.parse("redis:3-alpine"))
            .withExposedPorts(6379)

    @DynamicPropertySource
    static void redisProperties(DynamicPropertyRegistry registry) {
        redis.start();
        registry.add("spring.redis.host", {  -> redis.getContainerIpAddress()});
        registry.add("spring.redis.port", {  -> redis.getFirstMappedPort() });
    }

    @Autowired
    UserService userService

    def "cache integration test"() {

        when:

        def user = userService.insert(new UserDO("Peter"))
        def queryUser = userService.findItemById(user.getId())

        then:

        thrown BusinessException

        when:

        user = userService.insert(new UserDO("Peter", 18))

        then:

        user.id != null

        when:

        def users = userService.findAll(new UserDO("Peter"))

        then:

        users.size() == 1

        when:

        def query = new UserDO("Pe")
        query.addQueryComparator(new QueryComparator("name", SearchOperation.MATCH))
        users = userService.findAll(query)

        then:

        users.size() == 1

        when:

        query = new UserDO("Pe")
        query.addQueryComparator(new QueryComparator("name", SearchOperation.EQUAL))
        users = userService.findAll(query)

        then:

        users.size() == 0

        when:

        query = new UserDO()
        query.addQueryComparator(new QueryComparator("age", 10, SearchOperation.GREATER_THAN_EQUAL))
        users = userService.findAll(query)

        then:

        users.size() == 1

        when:

        query = new UserDO()
        query.addQueryComparator(new QueryComparator("age", 20, SearchOperation.GREATER_THAN_EQUAL))
        users = userService.findAll(query)

        then:

        users.size() == 0

        when:

        query = new UserDO()
        query.addQueryComparator(new QueryComparator("age", 20, SearchOperation.LESS_THAN_EQUAL))
        users = userService.findAll(query)

        then:

        users.size() == 1

        when:

        query = new UserDO()
        query.addQueryComparator(new QueryComparator("age", 10, SearchOperation.LESS_THAN_EQUAL))
        users = userService.findAll(query)

        then:

        users.size() == 0

        when:

        query = new UserDO()
        query.addQueryComparator(new QueryComparator("age", 18))
        users = userService.findAll(query)

        then:

        users.size() == 1


    }


}
