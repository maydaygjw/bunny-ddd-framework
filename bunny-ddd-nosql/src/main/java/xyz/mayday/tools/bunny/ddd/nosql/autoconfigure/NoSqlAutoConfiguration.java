package xyz.mayday.tools.bunny.ddd.nosql.autoconfigure;

import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.repository.mongo.MongoRepository;
import org.javers.spring.boot.mongo.JaversMongoAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.client.MongoDatabase;

@Configuration
@AutoConfigureBefore(JaversMongoAutoConfiguration.class)
public class NoSqlAutoConfiguration {
    
    @Bean
    Javers javas(MongoTemplate mongoTemplate) {
        MongoDatabase mongoDb = mongoTemplate.getDb();
        MongoRepository mongoRepository = new MongoRepository(mongoDb);
        return JaversBuilder.javers().registerJaversRepository(mongoRepository).build();
    }
    
}
