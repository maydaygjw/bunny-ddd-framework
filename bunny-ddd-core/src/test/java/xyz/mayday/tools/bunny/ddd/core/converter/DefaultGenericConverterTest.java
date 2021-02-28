package xyz.mayday.tools.bunny.ddd.core.converter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.mayday.tools.bunny.ddd.schema.converter.GenericConverter;
import xyz.mayday.tools.bunny.ddd.schema.domain.BaseVO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


class DefaultGenericConverterTest {

    GenericConverter converter;

    @BeforeEach
    void setup() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        converter = new DefaultGenericConverter(mapper);
    }

    @Test
    public void convert() {
        UserVO userVO = new UserVO("1", "Bill", 1, 1);
        UserQuery query = converter.convert(userVO, UserQuery.class);
        assertEquals("Bill", query.getName());
        assertEquals(1L, query.getId());
        assertNull(query.getDesc());
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class UserVO implements BaseVO<String> {
        String id;
        String name;
        Integer version;
        Integer revision;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    static class UserQuery {
        String name;
        Long id;
        String desc;
    }
}