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
@Service
public interface girlInfoDealService {
    public List<Girlnfo> getAllGirls();


}
