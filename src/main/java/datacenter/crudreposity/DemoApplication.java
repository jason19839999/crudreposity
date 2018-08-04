package datacenter.crudreposity;

import datacenter.crudreposity.dao.mysql.girlRepository;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
//@MapperScan("datacenter.crudreposity.service")
public class DemoApplication {

    @Autowired
    private girlRepository objgirlRepository;

    // need to set vm.options: -Dspring.profiles.active=dev
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(DemoApplication.class);
        springApplication.run(args);
        SpringApplication.run(DemoApplication.class, args);
    }

}
