package datacenter.crudreposity;

import datacenter.crudreposity.config.MysqlConnectionSettings;
import datacenter.crudreposity.config.RedisConnectionSettings;
import datacenter.crudreposity.service.Impl.girlInfoDealServiceImpl;
import datacenter.crudreposity.service.girlInfoDealService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;



/**
 * @描述
 * @创建人 shicong.zhang
 * @创建时间 $date$
 * @修改人和其它信息
 */
@Component
public class AppContext {

    @Bean
    public girlInfoDealService GirlInfoDealService(){return new girlInfoDealServiceImpl();}
    @Bean
    public MysqlConnectionSettings mysqlConnectionSettings(){return new MysqlConnectionSettings();}
    //@Bean
    //public RedisConnectionSettings redisConnectionSettings(){return new RedisConnectionSettings();}



}
