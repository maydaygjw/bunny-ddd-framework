package xyz.mayday.tools.bunny.ddd.sample;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class MessageConfiguration {

    @Bean
    public Function<String, String> uppercase() {
        return String::toUpperCase;
    }
}
