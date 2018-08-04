package datacenter.crudreposity;

import datacenter.crudreposity.dao.mysql.girlRepository;
import datacenter.crudreposity.entity.Girlnfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
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
