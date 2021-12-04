package xyz.mayday.tools.bunny.ddd.core.query.visit;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import javax.persistence.Transient;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import xyz.mayday.tools.bunny.ddd.core.domain.AbstractBaseDTO;
import xyz.mayday.tools.bunny.ddd.schema.query.QueryComparator;
import xyz.mayday.tools.bunny.ddd.schema.query.SearchCriteria;
import xyz.mayday.tools.bunny.ddd.schema.query.SearchOperation;

@Slf4j
public class MultipleValueCriteriaVisitorImpl extends BaseQuerySpecVisitor {
    
    @Override
    public void visit(AbstractBaseDTO<?> dto) {
        
        Map<String, Collection<?>> multipleValueAttributes = dto.getMultipleValueAttributes();
        List<SearchCriteria> collect = multipleValueAttributes.entrySet().stream().filter(entry -> {
            return FieldUtils.getFieldsListWithAnnotation(dto.getClass(), Transient.class).stream().map(Field::getName)
                    .noneMatch(fieldName -> fieldName.equals(entry.getKey()));
        }).filter(entry -> {
            return Arrays.stream(FieldUtils.getAllFields(dto.getClass())).anyMatch(f -> f.getName().equals(entry.getKey()));
        }).map(entry -> {
            
            String key = entry.getKey();
            Collection<Object> values = entry.getValue().stream().map(this::processValue).collect(Collectors.toSet());
            QueryComparator comparator = ObjectUtils.defaultIfNull(dto.getQueryComparators().get(key), new QueryComparator()
                    .withCompareWith(Collections.singletonList(key)).withKey(key).withSearchOperation(SearchOperation.IN).withValues(values));
            return comparator;
            
        }).map(this::toCriteria).collect(Collectors.toList());
        
        log.trace("Multiple values attribute: {}", collect);
        
        getSearchCriteria().addAll(collect);
    }
}
