package xyz.mayday.tools.bunny.ddd.core.service

import org.javers.core.JaversBuilder
import spock.lang.Specification
import xyz.mayday.tools.bunny.ddd.core.domain.Domain

class DefaultHistoryServiceTest extends Specification {

    def "FindHistoriesById"() {

        given:

        def javers = JaversBuilder.javers().build()
        def historyService = new DefaultHistoryService(javers)

        def user = new Domain.UserDAO()
        user.id = 1

        when:

        user.userName = "Bob"
        user.age = 18

        user.with {
            createdBy = "Modifier"
            updatedBy = "Modifier"
        }
        historyService.commit(user);

        def userResult = historyService.findHistoriesById(1L, Domain.UserDAO.class)

        then:

        userResult.size() == 1

        when:

        user.age = 20
        user.updatedBy = "Modifier"
        historyService.commit(user)
        userResult = historyService.findHistoriesById(1L, Domain.UserDAO.class)

        then:

        userResult.size() == 2
    }
}
