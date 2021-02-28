package xyz.mayday.tools.bunny.ddd.schema.converter;

/**
 * @author gejunwen
 */
public interface GenericConverter {

    <S, D> D convert(S s, Class<D> dClass);
}
