package xyz.mayday.tools.bunny.ddd.core.service

import spock.lang.Specification

class AbstractBaseServiceTest extends Specification {

    def "one plus one should equals two" () {
        expect:
        1 + 1 == 2
    }
}
