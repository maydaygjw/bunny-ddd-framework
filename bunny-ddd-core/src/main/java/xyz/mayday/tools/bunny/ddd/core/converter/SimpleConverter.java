package xyz.mayday.tools.bunny.ddd.core.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import xyz.mayday.tools.bunny.ddd.utils.jackson.ObjectMapperFactory;

public class SimpleConverter {

  static ObjectMapper mapper;

  static {
    mapper = ObjectMapperFactory.create();
  }

  public static <S, D> D convert(S source, Class<D> destination) {
    return mapper.convertValue(source, destination);
  }

  @SuppressWarnings("unchecked")
  public static <S> S clone(S source) {
    return (S) mapper.convertValue(source, source.getClass());
  }
}
