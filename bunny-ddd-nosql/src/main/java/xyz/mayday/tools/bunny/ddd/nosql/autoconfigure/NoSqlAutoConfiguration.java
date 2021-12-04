package xyz.mayday.tools.bunny.ddd.nosql.autoconfigure;

import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.repository.mongo.MongoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

@Configuration()
public class NoSqlAutoConfiguration {
    
    @Bean
    @Primary
    Javers javas(MongoClient mongoClient) {
        MongoDatabase mongoDb = mongoClient.getDatabase("revinfo");
        MongoRepository mongoRepository = new MongoRepository(mongoDb);
        return JaversBuilder.javers().registerJaversRepository(mongoRepository).build();
    }
    
}
