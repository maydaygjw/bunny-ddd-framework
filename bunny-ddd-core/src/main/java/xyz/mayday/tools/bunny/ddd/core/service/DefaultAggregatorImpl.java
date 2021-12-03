package xyz.mayday.tools.bunny.ddd.core.service;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import xyz.mayday.tools.bunny.ddd.schema.domain.BaseDomain;
import xyz.mayday.tools.bunny.ddd.schema.service.DomainAggregator;

public class DefaultAggregatorImpl implements DomainAggregator {
    @Override
    public <S, D extends BaseDomain<?>> void join(List<BaseDomain<D>> records, Class<S> targetClass, List<Pair<String, String>> joinMappings,
            List<Pair<String, String>> valueMappings) {
        
    }
}
