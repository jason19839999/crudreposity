package datacenter.crudreposity.dao.redis;

import datacenter.crudreposity.entity.Girlnfo;

/**
 * @描述
 * @创建人 shicong.zhang
 * @创建时间 $date$
 * @修改人和其它信息
 */
public interface girlInfoRedisDao {
    void save(Girlnfo obj);
    Girlnfo read(String id);

}
