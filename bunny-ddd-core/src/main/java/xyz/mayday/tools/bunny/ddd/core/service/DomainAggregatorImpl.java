package xyz.mayday.tools.bunny.ddd.core.service;

import org.apache.commons.lang3.tuple.Pair;
import xyz.mayday.tools.bunny.ddd.schema.domain.BaseDomain;
import xyz.mayday.tools.bunny.ddd.schema.service.DomainAggregator;

import java.util.List;

public class DomainAggregatorImpl implements DomainAggregator {
    @Override
    public <S, D extends BaseDomain<?>> void join(List<BaseDomain<D>> records, Class<S> targetClass, List<Pair<String, String>> joinMappings, List<Pair<String, String>> valueMappings) {

    }
}
