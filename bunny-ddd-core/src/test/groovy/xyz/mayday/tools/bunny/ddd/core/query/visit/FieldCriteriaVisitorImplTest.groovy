package xyz.mayday.tools.bunny.ddd.core.query.visit


import spock.lang.Specification
import xyz.mayday.tools.bunny.ddd.core.domain.AbstractBaseDTO
import xyz.mayday.tools.bunny.ddd.core.domain.Domain

class FieldCriteriaVisitorImplTest extends Specification {

    def "Visit"() {
        given:

        AbstractBaseDTO<Long> dto = new Domain.UserDTO()
        dto.age = 21
        dto.userName = "U1"

        def visitor = new FieldCriteriaVisitorImpl()

        when:

        dto.accept(visitor)

        then:

        visitor.querySpecifications.size() == 2


    }

}
