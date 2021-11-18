package xyz.mayday.tools.bunny.ddd.utils.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperFactory {

    public static ObjectMapper create() {
        return new ObjectMapper();
    }
}
