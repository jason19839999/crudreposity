package datacenter.crudreposity.service;


import datacenter.crudreposity.entity.Girlnfo;
import datacenter.crudreposity.entity.News;
import org.springframework.stereotype.Service;

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

public interface girlInfoDealService {
    public List<Girlnfo> getAllGirls();


    public List<News> getNews();
}
