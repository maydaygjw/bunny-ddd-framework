package xyz.mayday.tools.bunny.ddd.core.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import xyz.mayday.tools.bunny.ddd.schema.query.CommonQueryParam;

/** @author gejunwen */
public class QueryUtils {
    
    public static PageRequest buildPageRequest(CommonQueryParam queryParam) {
        return PageRequest.of(queryParam.getCurrentPage() - 1, queryParam.getPageSize(), Sort.by(queryParam.collectSortOrders()));
    }
}
