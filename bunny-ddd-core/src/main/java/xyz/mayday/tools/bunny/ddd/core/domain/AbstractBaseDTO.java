package xyz.mayday.tools.bunny.ddd.core.domain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.mayday.tools.bunny.ddd.schema.domain.BaseDomain;
import xyz.mayday.tools.bunny.ddd.schema.query.QueryComparator;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class AbstractBaseDTO<ID extends Serializable> extends AbstractBaseDAO<ID> implements BaseDomain<ID>, Visitable {
    
    Integer revision;
    
    String operationType;
    
    Map<String, QueryComparator> queryComparators;
    
    Map<String, Collection<?>> multipleValueAttributes;
    
    public AbstractBaseDTO() {
        queryComparators = new ConcurrentHashMap<>();
        multipleValueAttributes = new ConcurrentHashMap<>();
    }
    
    public void addQueryComparator(QueryComparator queryComparator) {
        queryComparators.put(queryComparator.getKey(), queryComparator);
    }
    
    public void addQueryComparators(QueryComparator... queryComparators) {
        this.queryComparators.putAll(Arrays.stream(queryComparators).collect(Collectors.toMap(QueryComparator::getKey, q -> q)));
    }
    
    public <T> void addMultiValues(String key, Collection<T> values) {
        multipleValueAttributes.put(key, values);
    }
    
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
    
    public Class<? extends AbstractBaseDAO<?>> getDaoClass() {
        return null;
    }
    
}
