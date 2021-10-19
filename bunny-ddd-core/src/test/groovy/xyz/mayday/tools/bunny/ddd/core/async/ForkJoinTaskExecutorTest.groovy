package xyz.mayday.tools.bunny.ddd.core.async

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import spock.lang.Specification

import java.util.concurrent.Callable
import java.util.stream.Collectors

class ForkJoinTaskExecutorTest extends Specification {

    def "submit"() {

        given:
        def executor = new ThreadPoolTaskExecutor();
        executor.corePoolSize = 10
        executor.initialize()

        def forkJoinPool = new ForkJoinTaskExecutor(executor)
        def submitList = [1, 2, 3].stream().map({ i ->
            return new Callable<Integer>() {
                @Override
                Integer call() throws Exception {
                    Thread.sleep(1000)
                    return i + 1 as Integer
                }
            }
        }).collect(Collectors.toList())
        when:
        def submitted = forkJoinPool.submit(submitList)
        then:
        submitted.stream().reduce({ a, c -> a + c }).orElse(0) == 9
    }
}
