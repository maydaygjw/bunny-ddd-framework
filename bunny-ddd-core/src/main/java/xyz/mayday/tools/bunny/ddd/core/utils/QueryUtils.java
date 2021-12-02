package xyz.mayday.tools.bunny.ddd.core.utils;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ReflectionUtils;

import xyz.mayday.tools.bunny.ddd.core.query.QuerySpecification;
import xyz.mayday.tools.bunny.ddd.schema.domain.BaseDAO;
import xyz.mayday.tools.bunny.ddd.schema.domain.BaseDomain;
import xyz.mayday.tools.bunny.ddd.schema.query.CommonQueryParam;
import xyz.mayday.tools.bunny.ddd.schema.query.SearchCriteria;

/** @author gejunwen */
public class QueryUtils {
    
    public static PageRequest buildPageRequest(CommonQueryParam queryParam) {
        return PageRequest.of(queryParam.getCurrentPage() - 1, queryParam.getPageSize(), Sort.by(queryParam.collectSortOrders()));
    }
}
