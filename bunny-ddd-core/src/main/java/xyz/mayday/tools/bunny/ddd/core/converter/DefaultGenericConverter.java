package xyz.mayday.tools.bunny.ddd.core.converter;

import lombok.RequiredArgsConstructor;
import xyz.mayday.tools.bunny.ddd.schema.converter.GenericConverter;

import com.fasterxml.jackson.databind.ObjectMapper;

@RequiredArgsConstructor
public class DefaultGenericConverter implements GenericConverter {
    
    final ObjectMapper objectMapper;
    
    @Override
    public <S, D> D convert(S s, Class<D> dClass) {
        return objectMapper.convertValue(s, dClass);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <S> S clone(S s) {
        return convert(s, (Class<S>) s.getClass());
    }
}
