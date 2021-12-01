package xyz.mayday.tools.bunny.ddd.core.query.visit;

import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

import javax.persistence.Transient;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.apache.commons.lang3.tuple.Pair;

import xyz.mayday.tools.bunny.ddd.core.domain.AbstractBaseDTO;
import xyz.mayday.tools.bunny.ddd.schema.query.QueryComparator;
import xyz.mayday.tools.bunny.ddd.schema.query.SearchCriteria;
import xyz.mayday.tools.bunny.ddd.schema.query.SearchOperation;
import xyz.mayday.tools.bunny.ddd.utils.ReflectionUtils;

public class FieldCriteriaVisitorImpl extends BaseQuerySpecVisitor {

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void visit(AbstractBaseDTO<?> dto) {

        List<SearchCriteria> collect = Arrays.stream(FieldUtils.getAllFields(dto.getClass()))
                .filter(field -> !FieldUtils.getFieldsListWithAnnotation(dto.getClass(), Transient.class).contains(field))
                .filter(field -> !TypeUtils.isArrayType(field.getType()))
                .filter(field -> !TypeUtils.isAssignable(field.getType(), Collection.class))
                .filter(field -> !TypeUtils.isAssignable(field.getType(), Map.class))
                .filter(field -> Objects.nonNull(ReflectionUtils.getValue(field, dto)))
                .filter(field -> !Modifier.isStatic(field.getModifiers()))
                .map(field -> Pair.of(field.getName(), ReflectionUtils.getValue(field, dto)))
                .map(pair -> {
                    String key = pair.getLeft();
                    Object value = processValue(pair.getRight());
                    QueryComparator<?> queryComparator = ObjectUtils.defaultIfNull(dto.getQueryComparators().get(key),
                            new QueryComparator<>().withKey(key).withCompareWith(key).withSearchOperation(SearchOperation.IN).withValues(Collections.singleton(value)));

                    return queryComparator;
                }).map(comparator -> {
                    return new SearchCriteria()
                            .withKey(comparator.getKey())
                            .withValues(comparator.getValues())
                            .withSearchOperation(comparator.getSearchOperation())
                            .withSearchConjunction(comparator.getSearchConjunction())
                            .withConjunctionGroup(comparator.getConjunctionGroup());
                }).collect(Collectors.toList());

        querySpecifications.addAll(collect);

    }
}
