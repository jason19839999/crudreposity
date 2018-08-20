package datacenter.crudreposity.dao.mysql2;

import datacenter.crudreposity.entity.mysql2.Girlnfo2;
import datacenter.crudreposity.entity.mysql2.HK_ShareIPOModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * @描述
 * @创建人 shicong.zhang
 * @创建时间 $date$
 * @修改人和其它信息
 */

@Repository
public interface UserRepository extends CrudRepository<HK_ShareIPOModel, Long> {
    @Query(name = "getHK_ShareIPOModelNames", value = "SELECT  name  FROM hk_shareipo ORDER BY  proposedlistdate DESC LIMIT 10", nativeQuery = true)
    public ArrayList<String> getHK_ShareIPOModelNames();


}
