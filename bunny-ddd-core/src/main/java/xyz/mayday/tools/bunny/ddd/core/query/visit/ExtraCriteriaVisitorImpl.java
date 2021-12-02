package xyz.mayday.tools.bunny.ddd.core.query.visit;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.Transient;

import org.reflections.ReflectionUtils;

import com.google.common.collect.Lists;

import xyz.mayday.tools.bunny.ddd.core.domain.AbstractBaseDTO;
import xyz.mayday.tools.bunny.ddd.schema.exception.BusinessException;
import xyz.mayday.tools.bunny.ddd.schema.query.SearchCriteria;
import xyz.mayday.tools.bunny.ddd.schema.query.SearchOperation;

public class ExtraCriteriaVisitorImpl extends BaseQuerySpecVisitor {
    
    @Override
    public void visit(AbstractBaseDTO<?> dto) {
        
        List<String> illegibleFields = ReflectionUtils.getAllFields(dto.getClass(), field -> Objects.isNull(field.getAnnotation(Transient.class))).stream()
                .map(Field::getName).collect(Collectors.toList());

        List<SearchCriteria> collect = dto.getQueryComparators().values().stream().filter(comparator -> illegibleFields.contains(comparator.getCompareWith())).map(comparator -> {

            if(comparator.getValues().size() > 1) {

                if (comparator.getSearchOperation().equals(SearchOperation.EQUALS)) {
                    comparator.setSearchOperation(SearchOperation.IN);
                }
                if (!Lists.newArrayList(SearchOperation.IN, SearchOperation.NOT_IN, SearchOperation.MATCH).contains(comparator.getSearchOperation())) {
                    throw new BusinessException();
                }

            }

            comparator.setValues(comparator.getValues().stream().map(this::processValue).collect(Collectors.toSet()));
            return toCriteria(comparator);

        }).collect(Collectors.toList());

        getSearchCriteria().addAll(collect);
    }
}
