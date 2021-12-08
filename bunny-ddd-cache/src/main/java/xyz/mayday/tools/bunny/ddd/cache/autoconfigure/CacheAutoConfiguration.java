package xyz.mayday.tools.bunny.ddd.cache.autoconfigure;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import xyz.mayday.tools.bunny.ddd.cache.query.CharacterIndexProcessor;
import xyz.mayday.tools.bunny.ddd.cache.query.RangeAvailableIndexProcessor;
import xyz.mayday.tools.bunny.ddd.schema.service.CacheableService;

@Configuration
@EnableConfigurationProperties({ RedisProperties.class, CacheProperties.class })
@ConditionalOnProperty(prefix = "bunny.ddd.cache", value = "enabled", havingValue = "true")
@AutoConfigureBefore(RedisAutoConfiguration.class)
@Slf4j
public class CacheAutoConfiguration {
    
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());
        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }
    
    @Autowired
    ApplicationContext context;
    
    @Autowired
    RedisProperties redisProperties;
    
    @Autowired
    CacheProperties cacheProperties;
    
    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(redisProperties.getHost(), redisProperties.getPort()));
    }
    
    @Bean
    CharacterIndexProcessor characterizeProcessor() {
        return new CharacterIndexProcessor(redisTemplate());
    }
    
    @Bean
    RangeAvailableIndexProcessor rangeAvailableProcessor() {
        return new RangeAvailableIndexProcessor(redisTemplate());
    }
    
    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            if (cacheProperties.isInit()) {
                log.info("Application about to init cache data");
                context.getBeansOfType(CacheableService.class).values().forEach(bean -> {
                    bean.destroyCache();
                    bean.createCache();
                    bean.initCacheData();
                });
            }
        };
        
    }
    
}
