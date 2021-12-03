package xyz.mayday.tools.bunny.ddd.utils.convert;

import xyz.mayday.tools.bunny.ddd.utils.jackson.ObjectMapperFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SimpleConverter {
    
    static final ObjectMapper objectMapper = ObjectMapperFactory.create();
    
    public static <S, D> D convert(S s, Class<D> dClass) {
        return objectMapper.convertValue(s, dClass);
    }
}
