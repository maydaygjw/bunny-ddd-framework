package xyz.mayday.tools.bunny.ddd.core.query.visit;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.apache.commons.lang3.tuple.Pair;
import xyz.mayday.tools.bunny.ddd.core.domain.AbstractBaseDTO;
import xyz.mayday.tools.bunny.ddd.schema.query.QueryComparator;
import xyz.mayday.tools.bunny.ddd.schema.query.SearchCriteria;
import xyz.mayday.tools.bunny.ddd.utils.ReflectionUtils;

import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

public class FieldCriteriaVisitorImpl extends BaseQuerySpecVisitor {

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void visit(AbstractBaseDTO<?> dto) {

        List<SearchCriteria> collect = Arrays.stream(FieldUtils.getAllFields(dto.getClass()))
                .filter(field -> !TypeUtils.isArrayType(field.getType()))
                .filter(field -> !TypeUtils.isAssignable(field.getType(), Collection.class))
                .filter(field -> TypeUtils.isAssignable(field.getType(), Map.class))
                .filter(field -> Objects.nonNull(ReflectionUtils.getValue(field, dto)))
                .filter(field -> !Modifier.isStatic(field.getModifiers()))
                .map(field -> Pair.of(field.getName(), ReflectionUtils.getValue(field, dto)))
                .map(pair -> {
                    String key = pair.getLeft();
                    QueryComparator<?> queryComparator = ObjectUtils.defaultIfNull(dto.getQueryComparators().get(key),
                            new QueryComparator<>().withKey(key).withCompareWith(key).withValues(Collections.singleton(pair.getRight())));

                    return queryComparator;
                }).map(comparator -> {
                    return new SearchCriteria()
                            .withKey(comparator.getKey())
                            .withValues(comparator.getValues())
                            .withSearchConjunction(comparator.getSearchConjunction())
                            .withConjunctionGroup(comparator.getConjunctionGroup());
                }).collect(Collectors.toList());

        querySpecifications.addAll(collect);

    }
}
