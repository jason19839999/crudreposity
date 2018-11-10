package datacenter.crudreposity.service.Impl;

import datacenter.crudreposity.config.ConnectionJustify;
import datacenter.crudreposity.config.DbContextHolder;
import datacenter.crudreposity.dao.mysql.girlRepository;
import datacenter.crudreposity.dao.mysql.newsRepository;
import datacenter.crudreposity.entity.Girlnfo;
import datacenter.crudreposity.entity.News;
import datacenter.crudreposity.entity.RedisScoreValue;
import datacenter.crudreposity.service.girlInfoDealService;
import org.hibernate.annotations.Synchronize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @描述
 * @创建人 shicong.zhang
 * @创建时间 $date$
 * @修改人和其它信息
 */

//*****************************************注意：添加数据库访问时，严格复制原来文件的头。不能有偏差，否则注入失败。包括XXXXRepository和entity文件头*****************************************
//注入失败可能有几种可能：
// ①没有加@Service 或者没有在@Component修饰的类里面加入@Bean，比如：这种类，AppContext.java
// ②注入的类里面有错误，比如：包括XXXXRepository和entity文件头，没有响应的配置，@Table(name = "news")

@Service  //添加了AppContext.java类，就不用@Service了，由于里面已经正如Bean了
//@Component    添加自动加载类，自动执行 例如：CacheCommandLineRunner，这个注解放到这里无用，只是说明用。。。
public class girlInfoDealServiceImpl implements girlInfoDealService {
    @Autowired
    private girlRepository objgirlRepository;          //注意：*****************************************    添加数据库访问时，严格复制原来文件的头。不能有偏差，否则注入失败。包括XXXXRepository和entity文件头*****************************************

    @Autowired
    private newsRepository objnewsRepository;    //注意：*****************************************   添加数据库访问时，严格复制原来文件的头。不能有偏差，否则注入失败。包括XXXXRepository和entity文件头*****************************************

    //事务控制
//    @Transactional
    public List<Girlnfo> getAllGirls(){
        List<Girlnfo> lst = objgirlRepository.getAllGirls();
        return lst;
    }

    @Override
    public List<News> getNews(){
           return objnewsRepository.getNews();
    }
}


