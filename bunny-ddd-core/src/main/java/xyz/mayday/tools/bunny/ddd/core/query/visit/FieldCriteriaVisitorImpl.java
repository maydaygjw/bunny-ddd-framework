package xyz.mayday.tools.bunny.ddd.core.query.visit;

import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

import javax.persistence.Transient;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.apache.commons.lang3.tuple.Pair;

import xyz.mayday.tools.bunny.ddd.core.domain.AbstractBaseDTO;
import xyz.mayday.tools.bunny.ddd.schema.query.SearchCriteria;
import xyz.mayday.tools.bunny.ddd.schema.query.SearchOperation;
import xyz.mayday.tools.bunny.ddd.utils.ReflectionUtils;

@Slf4j
public class FieldCriteriaVisitorImpl extends BaseQuerySpecVisitor {
    
    @Override
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void visit(AbstractBaseDTO<?> dto) {
        
        List<SearchCriteria> collect = Arrays.stream(FieldUtils.getAllFields(dto.getClass()))
                .filter(field -> !FieldUtils.getFieldsListWithAnnotation(dto.getClass(), Transient.class).contains(field))
                .filter(field -> !TypeUtils.isArrayType(field.getType())).filter(field -> !TypeUtils.isAssignable(field.getType(), Collection.class))
                .filter(field -> !TypeUtils.isAssignable(field.getType(), Map.class)).filter(field -> Objects.nonNull(ReflectionUtils.getValue(field, dto)))
                .filter(field -> !Modifier.isStatic(field.getModifiers())).filter(field -> Objects.isNull(dto.getQueryComparators().get(field.getName())))
                .map(field -> Pair.of(field.getName(), ReflectionUtils.getValue(field, dto))).map(pair -> {
                    String key = pair.getLeft();
                    Object value = processValue(pair.getRight());
                    return new xyz.mayday.tools.bunny.ddd.schema.query.QueryComparator().withKey(key).withCompareWith(Collections.singletonList(key))
                            .withSearchOperation(SearchOperation.IN).withValues(Collections.singleton(value));
                }).map(this::toCriteria).collect(Collectors.toList());
        
        log.trace("Field criteria: {}", collect);
        
        searchCriteria.addAll(collect);
        
    }
}
