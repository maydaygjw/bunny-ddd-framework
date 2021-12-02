package xyz.mayday.tools.bunny.ddd.core.query.visit

import spock.lang.Specification
import xyz.mayday.tools.bunny.ddd.core.domain.Domain
import xyz.mayday.tools.bunny.ddd.schema.query.QueryComparator
import xyz.mayday.tools.bunny.ddd.schema.query.SearchConjunction
import xyz.mayday.tools.bunny.ddd.schema.query.SearchOperation

class ExtraCriteriaVisitorImplTest extends Specification {

    def "Visit"() {

        given:

        def dto = new Domain.UserDTO()
        dto.userName = "Peter Ma"

        dto.addQueryComparator(new QueryComparator("userName", "Peter Xie"))

        def visitor = new ExtraCriteriaVisitorImpl();

        when:

        visitor.visit(dto)

        then:

        visitor.getSearchCriteria().size() == 1

        when:

        QueryComparator q1 = new QueryComparator("ageUpperRange").withCompareWith("age").withSearchOperation(SearchOperation.GREATER_THAN_EQUAL).withValues(Collections.singletonList(30)).withSearchConjunction(SearchConjunction.OR)
        QueryComparator q2 = new QueryComparator("ageLowerRange").withCompareWith("age").withSearchOperation(SearchOperation.LESS_THAN_EQUAL).withValues(Collections.singletonList(10)).withSearchConjunction(SearchConjunction.OR)
        dto.addQueryComparators(q1, q2)

        visitor = new ExtraCriteriaVisitorImpl()

        visitor.visit(dto)

        then:
        visitor.getSearchCriteria().size() == 3
    }
}
