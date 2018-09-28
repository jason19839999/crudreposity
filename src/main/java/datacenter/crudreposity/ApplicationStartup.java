package datacenter.crudreposity;

import datacenter.crudreposity.dao.hbase.HBaseConnectionFactory;
import datacenter.crudreposity.config.ServingSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.io.IOException;

/**
 *
 */
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationStartup.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent)  {
        //try {
            //String conf = contextRefreshedEvent.getApplicationContext().getBean(ServingSettings.class).getHbase_conf_dir();
            //HBaseConnectionFactory.init(conf);
        //} catch (IOException e) {
            //logger.error(e.getMessage());
        //}
    }
}
