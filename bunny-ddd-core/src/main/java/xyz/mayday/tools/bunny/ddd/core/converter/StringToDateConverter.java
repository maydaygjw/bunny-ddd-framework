package xyz.mayday.tools.bunny.ddd.core.converter;

import java.util.Date;

import lombok.SneakyThrows;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.core.convert.converter.Converter;

public class StringToDateConverter implements Converter<String, Date> {
    
    @SneakyThrows
    @Override
    public Date convert(String source) {
        return DateFormatUtils.ISO_8601_EXTENDED_DATE_FORMAT.parse(source);
    }
}
