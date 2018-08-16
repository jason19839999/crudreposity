package datacenter.crudreposity.action;

import datacenter.crudreposity.config.MybatisSessionFactory;
import datacenter.crudreposity.dao.mybatis.HKBillsDao;
import datacenter.crudreposity.dao.redis.girlInfoRedisDao;
import datacenter.crudreposity.entity.Girlnfo;
import datacenter.crudreposity.entity.HKBill;
import datacenter.crudreposity.entity.RedisScoreValue;
import datacenter.crudreposity.entity.girlInfoListResponse;
import datacenter.crudreposity.service.girlInfoDealService;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    @Autowired
    private girlInfoRedisDao objgirlInfoRedisDao;

    @RequestMapping(value = "/getGirlInfo", method = RequestMethod.GET)
    public ResponseEntity<girlInfoListResponse> getNewsList() {
        List<Girlnfo> lst = objgirlInfoDealService.getAllGirls();
        //girlInfoListResponse obj = new girlInfoListResponse(lst);
        girlInfoListResponse obj = new girlInfoListResponse();
        obj.setKey("小雪老婆好呀");
        obj.setResponse_data(lst);

        return new ResponseEntity<girlInfoListResponse>(obj, HttpStatus.OK);
    }

    @RequestMapping(value = "/getRedis", method = RequestMethod.GET)
    public String getRedis() {

        //objgirlInfoRedisDao.save("set_age");
        String result = objgirlInfoRedisDao.read("name");
        //zadd set_age 100 10 100 9 100 8   100为SCORES，必须为整数 10/9/8 可以为数组或者字符串
        //List<Integer> listInt = objgirlInfoRedisDao.readSetIntList("set_age");

        //zadd set_age 100 榆树 100 北京 100 河北   100为SCORES，必须为整数 10/9/8 可以为数组或者字符串
        List<String> listStr = objgirlInfoRedisDao.readSetStrList("set_age");

        //获取带有scores的set集合，想用value可以，想用scores也可以
        //List<Double> listDouble = objgirlInfoRedisDao.readSetStrListWithScores("set_age");

        //获取Score-Value
        List<RedisScoreValue> lst = objgirlInfoRedisDao.readRedisScoreValue("set_age");
        //倒序
        Collections.sort(lst, new Comparator<RedisScoreValue>() {
            @Override
            public int compare(RedisScoreValue o1, RedisScoreValue o2) {
                // 按照新闻时间进行降序排列
                if (o1.getScore() > o2.getScore()) {
                    return -1;
                }
                if (o1.getScore() == o2.getScore()) {
                    return 0;
                }
                return 1;
            }
        });


        objgirlInfoRedisDao.saveHash("set_hash","name","hello,jason,goodafternoon");
        objgirlInfoRedisDao.saveHash("set_hash","age","28");
        RedisScoreValue obj = objgirlInfoRedisDao.readHash("set_hash");

        return obj.getValue() + "------" + obj.getScore();
    }

    @RequestMapping(value = "/getMybatis", method = RequestMethod.GET)
    public String getMybatis() {
        SqlSession sqlSession = MybatisSessionFactory.openSession("app_data");
        HKBillsDao hkBillsDao = sqlSession.getMapper(HKBillsDao.class);
        ArrayList<HKBill> hkBills = hkBillsDao.getAllBills();
        return "调用成功";
    }
}
