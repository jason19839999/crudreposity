package datacenter.crudreposity.service;


import datacenter.crudreposity.entity.Girlnfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @描述
 * @创建人 shicong.zhang
 * @创建时间 $date$
 * @修改人和其它信息
 */
//@Service    //添加了AppContext.java类，就不用@Service了，由于里面已经正如Bean了
public interface girlInfoDealService {
    public List<Girlnfo> getAllGirls();


}
