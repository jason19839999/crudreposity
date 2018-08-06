package datacenter.crudreposity.dao.redis;

import datacenter.crudreposity.entity.Girlnfo;
import org.springframework.stereotype.Service;

/**
 * @描述
 * @创建人 shicong.zhang
 * @创建时间 $date$
 * @修改人和其它信息
 */
@Service
public interface girlInfoRedisDao {
    void save();
    String read(String id);

}
