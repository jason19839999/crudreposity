package datacenter.crudreposity.dao.mongodb;


import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableMongoRepositories(basePackages = "com.neo.model.repository.primary",
        //mongoTemplateRef = PrimaryMongoConfig.MONGO_TEMPLATE)
public class PrimaryMongoConfig {

    protected static final String MONGO_TEMPLATE = "primaryMongoTemplate";
}
