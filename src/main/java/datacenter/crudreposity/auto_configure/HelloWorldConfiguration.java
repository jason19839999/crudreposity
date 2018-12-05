package datacenter.crudreposity.auto_configure;

import org.springframework.stereotype.Service;

/**
 * HelloWorld 配置
 *
 * @author
 * @since
 */
@Service
public class HelloWorldConfiguration {

      public String helloWorld() { // 方法名即 Bean 名称
        return "Hello,World 2019";
    }

}
