package xyz.mayday.tools.bunny.ddd.core.async


import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import spock.lang.Specification

class ForkJoinTaskExecutorTest extends Specification {

    def "Submit"() {

        new ForkJoinTaskExecutor(new ThreadPoolTaskExecutor(10)).submit()
    }
}
