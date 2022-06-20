package xyz.mayday.tools.bunny.ddd.core.domain;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.springframework.data.annotation.Transient;

import xyz.mayday.tools.bunny.ddd.schema.domain.BaseDomain;
import xyz.mayday.tools.bunny.ddd.schema.query.QueryComparator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class AbstractBaseDTO<ID extends Serializable> extends AbstractBaseDAO<ID> implements BaseDomain<ID>, Visitable {
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Transient
    Long revision;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Transient
    String globalId;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Transient
    String operationType;
    
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Transient
    List<String> changedProperties;
    
    @Transient
    @JsonIgnore
    Map<String, QueryComparator> queryComparators;
    
    @JsonIgnore
    @Transient
    Map<String, Collection<?>> multipleValueAttributes;
    
    protected AbstractBaseDTO() {
        queryComparators = new ConcurrentHashMap<>();
        multipleValueAttributes = new ConcurrentHashMap<>();
    }
    
    public void addQueryComparator(QueryComparator queryComparator) {
        queryComparators.put(queryComparator.getKey(), queryComparator);
    }
    
    public void addQueryComparators(QueryComparator... queryComparators) {
        this.queryComparators.putAll(Arrays.stream(queryComparators).collect(Collectors.toMap(QueryComparator::getKey, q -> q)));
    }
    
    public void addQueryComparators(Collection<QueryComparator> queryComparators) {
        this.queryComparators.putAll(queryComparators.stream().collect(Collectors.toMap(QueryComparator::getKey, q -> q)));
    }
    
    public <T> void addMultiValues(String key, Collection<T> values) {
        multipleValueAttributes.put(key, values);
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
    
    @JsonIgnore
    public <C extends Serializable> Class<C> getDaoClass() {
        return null;
    }
    
    @Override
    public String getGlobalId() {
        return globalId;
    }
}
