package xyz.mayday.tools.bunny.ddd.core.utils;

import org.apache.commons.lang3.EnumUtils;

public class ConstantUtils {
    
    public static <E extends Enum<E>> E deserialize(String input, Class<E> enumClass) {
        return EnumUtils.getEnumList(enumClass).stream().filter(e -> {
            return e.name().equals(input);
        }).findAny().orElse(null);
    }
}
