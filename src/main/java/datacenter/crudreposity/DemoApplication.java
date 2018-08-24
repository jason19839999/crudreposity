package datacenter.crudreposity;


import datacenter.crudreposity.config.MybatisSessionFactory;
import datacenter.crudreposity.config.SeekConstants;
import datacenter.crudreposity.config.State;
import datacenter.crudreposity.dao.mybatis.HKBillsDao;
import datacenter.crudreposity.entity.HKBill;
import org.apache.ibatis.session.SqlSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@SpringBootApplication
@EnableAsync
@Service
public class DemoApplication {

    // need to set vm.options: -Dspring.profiles.active=dev
    public static void main(String[] args) throws Exception {
        //SpringApplication springApplication = new SpringApplication(DemoApplication.class);
        //springApplication.addListeners(new ApplicationStartup());// Register listener of hbase Connection initialized.
        //springApplication.run(args);


        /* init mybatis session factory */
        String args2[] = new String[2];
        args2[0] = "-c";
        args2[1] = SeekConstants.CONF_DIR + "/creeper_service.properties";
        State state = new State(args2);
        MybatisSessionFactory.init(state);

        SpringApplication.run(DemoApplication.class, args);
    }

}
