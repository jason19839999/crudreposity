package datacenter.crudreposity;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableAsync
public class DemoApplication {

    // need to set vm.options: -Dspring.profiles.active=dev
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(DemoApplication.class);
        //springApplication.addListeners(new ApplicationStartup());// Register listener of hbase Connection initialized.
        springApplication.run(args);
        SpringApplication.run(DemoApplication.class, args);
    }

}
