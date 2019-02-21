package datacenter.crudreposity;


import datacenter.crudreposity.config.MybatisSessionFactory;
import datacenter.crudreposity.config.SeekConstants;
import datacenter.crudreposity.config.State;
import datacenter.crudreposity.dao.mybatis.HKBillsDao;
import datacenter.crudreposity.dao.redis.girlInfoRedisDao;
import datacenter.crudreposity.entity.Girlnfo;
import datacenter.crudreposity.entity.HKBill;
import datacenter.crudreposity.filters.TokenFilter;
import datacenter.crudreposity.filters.VersionFilter;
import datacenter.crudreposity.util.JsonUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@SpringBootApplication
@EnableAsync
@Service
@EnableScheduling
public class DemoApplication {

    //注意：除了Controller和Service层，其他地方如果通过@Autowire引用其他类的实例的时候
    //需要在这里注入一下，就可以通过@Autowire了。

    /** Filters */
    @Bean
    public TokenFilter tokenFilterobj(){
        return new TokenFilter();
    }

    @Bean
    public VersionFilter versionFilterobj(){
        return new VersionFilter();
    }

    //指定过滤某个接口
    @Bean
    public FilterRegistrationBean tokenFilter(){
        TokenFilter filter =  tokenFilterobj();  //这样做的目的是为了能在TokenFilter使用@Autowire。要不然注入为null....
        String [] arras = {"/denglu","/getAccess"};
        return filtersGen(filter, Arrays.asList(arras),null, "tokenFilter", 1);
    }
    @Bean
    public FilterRegistrationBean versionFilter(){
        VersionFilter filter =  versionFilterobj();  //这样做的目的是为了能在TokenFilter使用@Autowire。要不然注入为null....
        return filtersGen(filter, Arrays.asList("/getAppChannel"),null, "versionFilter", 1);
    }


    private FilterRegistrationBean filtersGen(Filter filter, List<String> uris, Map<String,String> params, String name, int order){
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);

        if(uris != null && uris.size() > 0) {
            for(String uri : uris)
                registration.addUrlPatterns(uri);
        }

        if(params != null && params.size() > 0) {
            for(Map.Entry<String,String> entry : params.entrySet()){
                registration.addInitParameter(entry.getKey(),entry.getValue());
            }

        }

        registration.setName(name);
        registration.setOrder(order);
        return registration;
    }


    // need to set vm.options: -Dspring.profiles.active=dev
    public static void main(String[] args) throws Exception {
        //SpringApplication springApplication = new SpringApplication(DemoApplication.class);
        //springApplication.addListeners(new ApplicationStartup());// Register listener of hbase Connection initialized.
        //springApplication.run(args);
        if (args.length == 4) {
            //在linux环境设置配置文件地址
            SeekConstants.CONF_DIR = args[1];
//            cmd="java $MAVEN_OPTS
//                    -cp $bin_dir$binary
//                    $main
//                     -c $conf_dir 1 1
//                    >> $log_dir$logfile 2>&1 &

        }
        /* init mybatis session factory */
        String args2[] = new String[2];
        args2[0] = "-c";
        args2[1] = SeekConstants.CONF_DIR + "/creeper_service.properties";
        State state = new State(args2);
        MybatisSessionFactory.init(state);
        SpringApplication.run(DemoApplication.class, args);
    }


}
