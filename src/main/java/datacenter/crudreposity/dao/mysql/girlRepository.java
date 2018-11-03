package datacenter.crudreposity.dao.mysql;

import datacenter.crudreposity.config.ConnectionJustify;
import datacenter.crudreposity.config.DbContextHolder;
import datacenter.crudreposity.entity.Girlnfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * @描述
 * @创建人 shicong.zhang
 * @创建时间 $date$
 * @修改人和其它信息
 */
@Repository
public interface girlRepository  extends CrudRepository<Girlnfo, Long> {
    //配置读写分离
    @ConnectionJustify(dbType = DbContextHolder.DbType.MASTER)
    @Query(name = "getAllGirls", value = "SELECT * FROM girl_info", nativeQuery = true)
    public ArrayList<Girlnfo> getAllGirls();



}
