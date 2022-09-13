package xyz.mayday.tools.bunny.ddd.qdsl.domain;

import java.io.Serializable;

import xyz.mayday.tools.bunny.ddd.core.domain.AbstractBaseDTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.types.dsl.EntityPathBase;

public abstract class BaseQueryableDTO<ID extends Serializable> extends AbstractBaseDTO<ID> {
    
    @JsonIgnore
    public abstract EntityPathBase<? extends BaseQueryableDTO<ID>> getQuery();
}
