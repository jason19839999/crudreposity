package datacenter.crudreposity.dao.mongodb;


import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

//@Data
@ConfigurationProperties(prefix = "mongodb")
public class Config {

    private MongoProperties primary = new MongoProperties();
    private MongoProperties secondary = new MongoProperties();
}