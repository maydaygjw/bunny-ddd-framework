package xyz.mayday.tools.bunny.ddd.core.controller

import spock.lang.Specification
import xyz.mayday.tools.bunny.ddd.schema.converter.GenericConverter
import xyz.mayday.tools.bunny.ddd.schema.page.PagingParameters
import xyz.mayday.tools.bunny.ddd.schema.query.CommonQueryParam
import xyz.mayday.tools.bunny.ddd.schema.service.BaseService

class BaseControllerImplTest extends Specification {

    def "test for applyQueryRestriction"() {
        given:
            def genericConverter = Mock(GenericConverter)
            def pagingConfigure = Mock(PagingParameters)

            pagingConfigure.defaultPageSize >> 30
            pagingConfigure.pageSizeLimit >> 10000

            def controller = new TestBaseControllerImpl(genericConverter, pagingConfigure)
            def commonQueryParam = new CommonQueryParam()
        when:
            controller.applyQueryRestriction(commonQueryParam)
        then:
            commonQueryParam.currentPage == 1
            commonQueryParam.pageSize == 30
            commonQueryParam.sortField == ["updatedDate"]
            commonQueryParam.sortOrder == ["DESC"]
    }

    static class TestBaseControllerImpl extends BaseControllerImpl {

        TestBaseControllerImpl(GenericConverter converter, PagingParameters pagingConfigure) {
            super(converter, pagingConfigure)
        }

        @Override
        BaseService getService() {
            return null
        }
    }


}
