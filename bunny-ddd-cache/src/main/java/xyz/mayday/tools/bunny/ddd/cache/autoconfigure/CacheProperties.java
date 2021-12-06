package xyz.mayday.tools.bunny.ddd.cache.autoconfigure;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "bunny.ddd.cache")
@Data
public class CacheProperties {
    
    boolean init;
    
    boolean enabled;
}
