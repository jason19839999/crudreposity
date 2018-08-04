package datacenter.crudreposity.action;

import datacenter.crudreposity.entity.Girlnfo;
import datacenter.crudreposity.entity.girlInfoListResponse;
import datacenter.crudreposity.service.girlInfoDealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<girlInfoListResponse> getNewsList() {
        List<Girlnfo> lst = objgirlInfoDealService.getAllGirls();
        //girlInfoListResponse obj = new girlInfoListResponse(lst);
        girlInfoListResponse obj = new girlInfoListResponse();
        obj.setKey("小雪老婆好呀");
        obj.setResponse_data(lst);

        return new ResponseEntity<girlInfoListResponse>(obj, HttpStatus.OK);
    }
}
