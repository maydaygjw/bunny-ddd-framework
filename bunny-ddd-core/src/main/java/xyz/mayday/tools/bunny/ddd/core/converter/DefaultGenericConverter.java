package xyz.mayday.tools.bunny.ddd.core.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.RequiredArgsConstructor;
import xyz.mayday.tools.bunny.ddd.schema.converter.GenericConverter;

@RequiredArgsConstructor
public class DefaultGenericConverter implements GenericConverter {

    final ObjectMapper objectMapper;

    @Override
    public <S, D> D convert(S s, Class<D> dClass) {
        return objectMapper.convertValue(s, dClass);
    }

    @Override
    public <S> S clone(S s) {
        return convert(s, (Class<S>)s.getClass());
    }
}
