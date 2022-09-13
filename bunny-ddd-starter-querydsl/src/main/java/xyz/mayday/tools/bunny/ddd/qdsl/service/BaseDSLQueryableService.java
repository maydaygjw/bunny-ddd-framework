package xyz.mayday.tools.bunny.ddd.qdsl.service;

import java.io.Serializable;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import xyz.mayday.tools.bunny.ddd.schema.domain.BaseDomain;
import xyz.mayday.tools.bunny.ddd.schema.service.BaseService;

public interface BaseDSLQueryableService<ID extends Serializable, DOMAIN extends BaseDomain<ID>> extends BaseService<ID, DOMAIN> {
    JPAQueryFactory getJpaQueryFactory();

    JPAQuery<DOMAIN> selectFrom(DOMAIN dQuery);


}
