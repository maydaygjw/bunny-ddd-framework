package xyz.mayday.tools.bunny.ddd.sample.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class SampleApplicationConfiguration {

////    @Bean
//    ReactiveRedisOperations<String, TodoDO> redisOperations(ReactiveRedisConnectionFactory factory) {
//        Jackson2JsonRedisSerializer<TodoDO> serializer = new Jackson2JsonRedisSerializer<>(TodoDO.class);
//        RedisSerializationContext.RedisSerializationContextBuilder<String, TodoDO> builder =
//                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());
//
//        RedisSerializationContext<String, TodoDO> context = builder.value(serializer).build();
//
//        return new ReactiveRedisTemplate<>(factory, context);
//    }
//
////    @Bean
//    CacheableService<Long, TodoDO> cacheableTodoService(TodoService todoService, ReactiveRedisOperations<String, TodoDO> reactiveRedisOperations) {
//        return new RedisCacheableServiceImpl<>(todoService, reactiveRedisOperations);
//    }
}
