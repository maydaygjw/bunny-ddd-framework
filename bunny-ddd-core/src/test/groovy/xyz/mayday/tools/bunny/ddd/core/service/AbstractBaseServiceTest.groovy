package xyz.mayday.tools.bunny.ddd.core.service

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject
import xyz.mayday.tools.bunny.ddd.core.converter.DefaultGenericConverter
import xyz.mayday.tools.bunny.ddd.core.domain.Domain
import xyz.mayday.tools.bunny.ddd.schema.auth.PrincipalService
import xyz.mayday.tools.bunny.ddd.schema.converter.GenericConverter
import xyz.mayday.tools.bunny.ddd.schema.page.PageableData
import xyz.mayday.tools.bunny.ddd.schema.query.CommonQueryParam
import xyz.mayday.tools.bunny.ddd.schema.service.HistoryService
import xyz.mayday.tools.bunny.ddd.schema.service.IdGenerator

import java.util.stream.Stream

class AbstractBaseServiceTest extends Specification {

    @Shared
    AbstractBaseService userBaseService

    def setup() {
        PrincipalService principalService = Mock()
        principalService.getCurrentUserId() >> "ddd-user1"
        def idGenerator = Mock(IdGenerator.class)
        def historyService = Mock(HistoryService)
        userBaseService = new UserBaseService(new DefaultGenericConverter(new ObjectMapper()), principalService, idGenerator, historyService)
    }

    def "test for auditWhenInsert"() {
        given:
        def user = new Domain.UserDAO("Bob", 12)
        when:
        userBaseService.auditWhenInsert(user)
        then:
        user.createdBy == "ddd-user1"
        user.updatedBy == "ddd-user1"
        user.createdDate != null
        user.updatedDate != null

    }

    def "test for auditWhenUpdate"() {
        given:
        def user = new Domain.UserDAO("Bob", 12)
        user.createdDate = new Date()
        user.createdBy = "ddd-user0"
        when:
        userBaseService.auditWhenUpdate(user)
        then:
        user.createdBy == "ddd-user0"
        user.updatedBy == "ddd-user1"
        user.createdDate != null
        user.updatedDate != null
    }

    def "test for get DAO class"() {
        expect:
        userBaseService.getDaoClass() == Domain.UserDAO.class
    }

    def "test for get DTO class"() {
        expect:
        userBaseService.getDtoClass() == Domain.UserDTO.class
    }

    def "test for get ID type"() {
        expect:
        userBaseService.getIdType() == Long.class
    }

    def "test for convert ID type"() {
        expect:
        userBaseService.convertIdType("1002") == 1002L
    }

    @Subject
    static class UserBaseService extends AbstractBaseService<Long, Domain.UserDTO, Domain.UserDAO> {

        UserBaseService(GenericConverter converter, PrincipalService principalService, IdGenerator<String> idGenerator, HistoryService historyService) {
            super(converter, principalService, idGenerator, historyService)
        }

        @Override
        Optional<Domain.UserDTO> findItemById(Long aLong) {
            return null
        }

        @Override
        List<Domain.UserDTO> findItemsByIds(List<Long> id) {
            return null
        }

        @Override
        List<Domain.UserDTO> findHistoriesById(Long aLong) {
            return null
        }

        @Override
        PageableData<Domain.UserDTO> doFindItems(Domain.UserDTO example, CommonQueryParam queryParam) {
            return null
        }

        @Override
        Long countItems(Domain.UserDTO example) {
            return null
        }

        @Override
        List<Domain.UserDTO> findAll(Domain.UserDTO example) {
            return null
        }

        @Override
        Stream<Domain.UserDTO> findStream(Domain.UserDTO example) {
            return null
        }

        @Override
        Domain.UserDTO insert(Domain.UserDTO userDTO) {
            return null
        }

        @Override
        List<Domain.UserDTO> bulkInsert(List<Domain.UserDTO> userDTOS) {
            return null
        }

        @Override
        Domain.UserDTO update(Domain.UserDTO userDTO) {
            return null
        }

        @Override
        List<Domain.UserDTO> bulkUpdate(List<Domain.UserDTO> userDTOS) {
            return null
        }

        @Override
        Domain.UserDTO save(Domain.UserDTO userDTO) {
            return null
        }

        @Override
        Domain.UserDTO delete(Long aLong) {
            return null
        }

        @Override
        List<Domain.UserDTO> bulkDeleteById(List<Long> longs) {
            return null
        }

        @Override
        List<Domain.UserDTO> deleteAll() {
            return null
        }
    }
}
