package xyz.mayday.tools.bunny.ddd.context.autoconfigure

import net.javacrumbs.shedlock.core.LockConfiguration
import net.javacrumbs.shedlock.core.LockProvider
import net.javacrumbs.shedlock.provider.inmemory.InMemoryLockProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import spock.lang.Specification

import java.time.Duration
import java.time.Instant

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Configuration
class TestDDDAutoConfiguration extends Specification{

    @Value('${my.encrypt.prop}')
    String prop

    def "test for get the encrypt value"() {
        expect:
        prop == "aaa"
    }

    def "test for shedLock"() {
        given:
        LockProvider provider = lockProvider()
        LockConfiguration configuration = new LockConfiguration(Instant.now(), "test", Duration.ofMinutes(1), Duration.ZERO)

        when:
        def locked = provider.lock(configuration);

        then:

        locked.present

        when:

        def locked2 = provider.lock(configuration)

        then:

        !locked2.present

        when:

        locked.get().unlock()
        def locked3 = provider.lock(configuration)

        then:

        locked3.present

        cleanup:
        locked3.get().unlock()
    }

    @Bean
    public LockProvider lockProvider() {
        return new InMemoryLockProvider();
    }

}
