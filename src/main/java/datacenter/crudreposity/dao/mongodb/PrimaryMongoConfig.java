package datacenter.crudreposity.dao.mongodb;

import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.repository.MongoRepository;



@Configuration
//@EnableMongoRepositories(basePackages = "com.neo.model.repository.primary",
        //mongoTemplateRef = PrimaryMongoConfig.MONGO_TEMPLATE)
public class PrimaryMongoConfig {

    protected static final String MONGO_TEMPLATE = "primaryMongoTemplate";
}