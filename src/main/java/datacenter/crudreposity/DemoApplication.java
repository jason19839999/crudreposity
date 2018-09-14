package datacenter.crudreposity;


import datacenter.crudreposity.config.MybatisSessionFactory;
import datacenter.crudreposity.config.SeekConstants;
import datacenter.crudreposity.config.State;
import datacenter.crudreposity.dao.mybatis.HKBillsDao;
import datacenter.crudreposity.dao.redis.girlInfoRedisDao;
import datacenter.crudreposity.entity.HKBill;
import datacenter.crudreposity.filters.TokenFilter;
import datacenter.crudreposity.filters.VersionFilter;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import javax.servlet.Filter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


@SpringBootApplication
@EnableAsync
@Service
public class DemoApplication {


    //指定过滤某个接口
    @Bean
    public FilterRegistrationBean tokenFilter(){
        TokenFilter filter =  new TokenFilter();
        String [] arras = {"/getCurrentNews","/getNewsListNewest","/getSubscribeTagNew","/saveUserSubscribeColumnsTag","/getHotNews","/saveViewPointAciton"};
        return filtersGen(filter, Arrays.asList(arras),null, "tokenFilter", 1);
    }
    @Bean
    public FilterRegistrationBean versionFilter(){
        VersionFilter filter =  new VersionFilter();
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

        double log1p =  Math.log1p(1);
        double log =  Math.log(3);
        double log10 = Math.log10(10000);
        /* init mybatis session factory */
        String args2[] = new String[2];
        args2[0] = "-c";
        args2[1] = SeekConstants.CONF_DIR + "/creeper_service.properties";
        State state = new State(args2);
        MybatisSessionFactory.init(state);
        SpringApplication.run(DemoApplication.class, args);
    }


}
