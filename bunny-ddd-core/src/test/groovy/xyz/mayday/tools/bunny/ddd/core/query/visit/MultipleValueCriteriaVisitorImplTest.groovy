package xyz.mayday.tools.bunny.ddd.core.query.visit

import spock.lang.Specification
import xyz.mayday.tools.bunny.ddd.core.domain.Domain

class MultipleValueCriteriaVisitorImplTest extends Specification {

    def "Visit"() {

        given:

        def dto = new Domain.UserDTO()
        dto.addMultiValues("userName", ["Bob", "Peter"])
        dto.addMultiValues("userNo", [1, 2, 3])
        dto.age = 18

        def visitor = new MultipleValueCriteriaVisitorImpl()

        when:

        visitor.visit(dto)

        then:

        visitor.querySpecifications.size() == 1
    }
}
