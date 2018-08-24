package datacenter.crudreposity.dao.mongodb;

import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableMongoRepositories(basePackages = "com.neo.model.repository.secondary",
        //mongoTemplateRef = SecondaryMongoConfig.MONGO_TEMPLATE)
public class SecondaryMongoConfig {

    protected static final String MONGO_TEMPLATE = "secondaryMongoTemplate";
}