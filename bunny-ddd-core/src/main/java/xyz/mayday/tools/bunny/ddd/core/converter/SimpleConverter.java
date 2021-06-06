package xyz.mayday.tools.bunny.ddd.core.converter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SimpleConverter {

    static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
    }

    public static <S, D> D convert(S source, Class<D> destination) {
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <S> S clone(S source) {
        return (S) mapper.convertValue(source, source.getClass());
    }
}
