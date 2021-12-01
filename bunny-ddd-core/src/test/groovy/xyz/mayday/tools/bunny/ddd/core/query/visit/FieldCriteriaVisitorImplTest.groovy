package xyz.mayday.tools.bunny.ddd.core.query.visit

import spock.lang.Specification
import xyz.mayday.tools.bunny.ddd.core.domain.Domain

class FieldCriteriaVisitorImplTest extends Specification {

    def "Visit"() {
        given:

        def dto = new Domain.UserDTO()
        dto.age = 21
        dto.userName = "U1"
        dto.userNo = 1
        dto.authorizations = ["auth1", "auth2"]

        def visitor = new FieldCriteriaVisitorImpl()

        when:

        dto.accept(visitor)

        then:

        visitor.querySpecifications.size() == 2
        visitor.querySpecifications[1].key == "age"
        visitor.querySpecifications[1].values == [21].toSet()


    }

}
