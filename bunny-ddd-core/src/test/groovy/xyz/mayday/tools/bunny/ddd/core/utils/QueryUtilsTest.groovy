package xyz.mayday.tools.bunny.ddd.core.utils


import spock.lang.Specification
import xyz.mayday.tools.bunny.ddd.core.domain.Domain
import xyz.mayday.tools.bunny.ddd.core.query.QueryCondition
import xyz.mayday.tools.bunny.ddd.schema.query.CommonQueryParam
import xyz.mayday.tools.bunny.ddd.schema.query.SearchConjunction
import xyz.mayday.tools.bunny.ddd.schema.query.SearchOperation

class QueryUtilsTest extends Specification {

    def "test for buildSpecification"() {

        given:
            def userDTO = new Domain.UserDTO();
            userDTO.with {userName = "Bill"}
        when:
            def queryCondition = (QueryCondition)QueryUtils.buildSpecification(userDTO)
        then:
            queryCondition.searchCriteriaList.size() == 1
            queryCondition.searchCriteriaList[0].key == "userName"
            queryCondition.searchCriteriaList[0].getValue() == "Bill"
            queryCondition.searchCriteriaList[0].getSearchConjunction() == SearchConjunction.AND
            queryCondition.searchCriteriaList[0].getSearchOperation() == SearchOperation.EQUALS
    }

    def "test for buildFieldCriteria"() {

    }

    def "test for buildPageRequest"() {
        when:
            def request = QueryUtils.buildPageRequest(new CommonQueryParam().withCurrentPage(5).withPageSize(30))
        then:
            request.getPageSize() == 30
            request.getPageNumber() == 4
    }
}
