package datacenter.crudreposity.service.Impl;

import datacenter.crudreposity.config.ConnectionJustify;
import datacenter.crudreposity.config.DbContextHolder;
import datacenter.crudreposity.dao.mysql.girlRepository;
import datacenter.crudreposity.entity.Girlnfo;
import datacenter.crudreposity.service.girlInfoDealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @描述
 * @创建人 shicong.zhang
 * @创建时间 $date$
 * @修改人和其它信息
 */

@Service
public class girlInfoDealServiceImpl implements girlInfoDealService {
    @Autowired
    private girlRepository objgirlRepository;

    //配置读写分离
    @ConnectionJustify(dbType = DbContextHolder.DbType.MASTER)
    public List<Girlnfo> getAllGirls(){
        List<Girlnfo> lst = objgirlRepository.getAllGirls();
        return lst;
    }
}
