package xyz.mayday.tools.bunny.ddd.qdsl.service;

import xyz.mayday.tools.bunny.ddd.schema.service.BaseService;

import com.querydsl.jpa.impl.JPAQueryFactory;

public interface BaseDSLQueryableService<ID, DOMAIN> extends BaseService<ID, DOMAIN> {
    JPAQueryFactory getJpaQueryFactory();
}
