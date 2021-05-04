package xyz.mayday.tools.bunny.ddd.core.controller

import spock.lang.Specification
import xyz.mayday.tools.bunny.ddd.schema.converter.GenericConverter
import xyz.mayday.tools.bunny.ddd.schema.page.PagingConfigure
import xyz.mayday.tools.bunny.ddd.schema.query.CommonQueryParam
import xyz.mayday.tools.bunny.ddd.schema.service.BaseService

class BaseControllerImplTest extends Specification {

    def "test for applyQueryRestriction"() {
        given:
            def genericConverter = Mock(GenericConverter.class)
            def pagingConfigure = Mock(PagingConfigure.class)

            pagingConfigure.defaultPageSize >> 20
            pagingConfigure.pageSizeLimit >> 10000

            def controller = new TestBaseControllerImpl(genericConverter, pagingConfigure)
            def commonQueryParam = new CommonQueryParam()
        when:
            controller.applyQueryRestriction(commonQueryParam)
        then:
            commonQueryParam.currentPage == 1
            commonQueryParam.pageSize == 20
            commonQueryParam.sortField == ["updatedDate"]
            commonQueryParam.sortOrder == ["DESC"]
    }

    static class TestBaseControllerImpl extends BaseControllerImpl {

        TestBaseControllerImpl(GenericConverter converter, PagingConfigure pagingConfigure) {
            super(converter, pagingConfigure)
        }

        @Override
        BaseService getService() {
            return null
        }
    }


}
