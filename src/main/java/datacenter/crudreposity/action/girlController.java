package datacenter.crudreposity.action;

import datacenter.crudreposity.dao.mysql.girlRepository;
import datacenter.crudreposity.entity.Girlnfo;
import datacenter.crudreposity.service.girlInfoDealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @描述
 * @创建人 shicong.zhang
 * @创建时间 $date$
 * @修改人和其它信息
 */
@RestController
public class girlController {

    @Autowired
    private girlInfoDealService objgirlInfoDealService;

    @RequestMapping(value = "/getGirlInfo", method = RequestMethod.GET)
    public List<Girlnfo> getNewsList(){
        List<Girlnfo> lst = objgirlInfoDealService.getAllGirls();

        lst = new ArrayList<>();

        return  lst;
    }
}
