package xyz.mayday.tools.bunny.ddd.core.query.visit;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.Transient;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import xyz.mayday.tools.bunny.ddd.core.converter.SimpleConverter;
import xyz.mayday.tools.bunny.ddd.core.domain.AbstractBaseDTO;
import xyz.mayday.tools.bunny.ddd.schema.query.QueryComparator;
import xyz.mayday.tools.bunny.ddd.schema.query.SearchCriteria;
import xyz.mayday.tools.bunny.ddd.schema.query.SearchOperation;

public class MultipleValueCriteriaVisitorImpl extends BaseQuerySpecVisitor {

    @Override
    public void visit(AbstractBaseDTO<?> dto) {

        Map<String, Collection<?>> multipleValueAttributes = dto.getMultipleValueAttributes();
        List<SearchCriteria> collect = multipleValueAttributes.entrySet().stream()
                .filter(entry -> {
                    return FieldUtils.getFieldsListWithAnnotation(dto.getClass(), Transient.class).stream()
                            .map(Field::getName)
                            .noneMatch(fieldName -> fieldName.equals(entry.getKey()));
                })
                .filter(entry -> {
                    return Arrays.stream(FieldUtils.getAllFields(dto.getClass())).anyMatch(f -> f.getName().equals(entry.getKey()));
                })
                .map(entry -> {

                    String key = entry.getKey();
                    Collection<Object> values = entry.getValue().stream().map(this::processValue).collect(Collectors.toSet());
                    QueryComparator<?> comparator = ObjectUtils.defaultIfNull(dto.getQueryComparators().get(key),
                            new QueryComparator<>().withCompareWith(key).withKey(key).withSearchOperation(SearchOperation.IN).withValues(values));
                    return comparator;

                }).map(comparator -> {
                    return SimpleConverter.convert(comparator, SearchCriteria.class);
                }).collect(Collectors.toList());

        getQuerySpecifications().addAll(collect);
    }
}
