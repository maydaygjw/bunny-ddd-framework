package xyz.mayday.tools.bunny.ddd.utils.convert;

import com.fasterxml.jackson.databind.ObjectMapper;
import xyz.mayday.tools.bunny.ddd.utils.jackson.ObjectMapperFactory;

public class SimpleConverter {

    static final ObjectMapper objectMapper = ObjectMapperFactory.create();

    public static <S, D> D convert(S s, Class<D> dClass) {
        return objectMapper.convertValue(s, dClass);
    }
}
