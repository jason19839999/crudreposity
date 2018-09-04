package datacenter.crudreposity.action;

import datacenter.crudreposity.aspect.Servicelock;
import datacenter.crudreposity.config.MybatisSessionFactory;
import datacenter.crudreposity.dao.mybatis.HKBillsDao;
import datacenter.crudreposity.dao.mysql2.UserMysqlRepository;
import datacenter.crudreposity.dao.redis.girlInfoRedisDao;
import datacenter.crudreposity.distributedlock.redis.RedissLockUtil;
import datacenter.crudreposity.entity.Girlnfo;
import datacenter.crudreposity.entity.HKBill;
import datacenter.crudreposity.entity.RedisScoreValue;
import datacenter.crudreposity.entity.girlInfoListResponse;
import datacenter.crudreposity.entity.mongodb.User;
import datacenter.crudreposity.entity.requestParam.UserLogin;
import datacenter.crudreposity.entity.responseParam.CodeMsg;
import datacenter.crudreposity.entity.responseParam.Result;
import datacenter.crudreposity.exception.GlobalException;
import datacenter.crudreposity.service.Impl.UserServiceMongodbImpl;
import datacenter.crudreposity.service.girlInfoDealService;
import datacenter.crudreposity.websocket.WebSocketServer;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.ibatis.session.SqlSession;
import org.redisson.api.*;
import org.redisson.client.codec.Codec;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPOutputStream;
import org.redisson.RedissonMultiLock;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;


/**
 * @描述
 * @创建人 shicong.zhang
 * @创建时间 $date$
 * @修改人和其它信息
 */
@Controller
public class girlController {

    @Autowired
    private girlInfoDealService objgirlInfoDealService;

    //为了方便测试，所以调用dao写在了controller层，正常应该写在service层。。。
    @Autowired
    private girlInfoRedisDao objgirlInfoRedisDao;

    @Autowired
    private UserMysqlRepository userRepository;

    @Autowired
    private UserServiceMongodbImpl userServiceMongodbImpl;


    @RequestMapping(value = "/getGirlInfo", method = RequestMethod.GET)
    public ResponseEntity<girlInfoListResponse> getNewsList() {

        //测试msql这个连接是否通了。这里面包含了读写分离
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


        objgirlInfoRedisDao.saveHash("set_hash", "name", "hello,jason,goodafternoon");
        objgirlInfoRedisDao.saveHash("set_hash", "age", "28");
        RedisScoreValue obj = objgirlInfoRedisDao.readHash("set_hash");

        return obj.getValue() + "------" + obj.getScore();
    }

    @RequestMapping(value = "/getMybatis", method = RequestMethod.GET)
    public String getMybatis() throws Exception {

        //创建mybatis连接  她所用的配置文件为creeper_service.properties，mybatis-setting.xml
        SqlSession sqlSession = MybatisSessionFactory.openSession("app_data");
        HKBillsDao hkBillsDao = sqlSession.getMapper(HKBillsDao.class);
        ArrayList<HKBill> hkBills = hkBillsDao.getAllBills();
        HKBill objHKBill = new HKBill();
        objHKBill.setBill_date("2018-08-20");
        objHKBill.setBill_type("daily");
        objHKBill.setCode("1000200");
        objHKBill.setEmail("");
        objHKBill.setName("");
        objHKBill.setPdf_location("");
        objHKBill.setPng_location("");
        objHKBill.setRowkey("adfaa");
        int result = hkBillsDao.insertBill(objHKBill);

        sqlSession.commit();
        sqlSession.close();

        //测试msql2这个连接是否通了
        List<String> strList = userRepository.getHK_ShareIPOModelNames();


        return "调用成功";
    }

    @RequestMapping(value = "/getMongodb", method = RequestMethod.GET)
    public String getMongodb() throws Exception {
        User objUser = new User();
        objUser.setId(1);
        objUser.setName("jason");
        objUser.setAge(18);
        //插入到数据库
        userServiceMongodbImpl.saveUser(objUser);

        //按照名字查询
        objUser = userServiceMongodbImpl.findUserByUserName(objUser.getName(), objUser.getAge());


        return "调用成功";
    }

    //@AccessLimit(seconds = 30)    //接口下流注入 比如：每分钟只能请求60次。思路：每次请求count放入redis,然后设置redis过期时间为1分钟，判断一分钟内不能超过60次。一分钟后过期重新计算
    @Servicelock   //实现分布式锁功能，主要采用了Lock,reentrantLock(true) 公平锁
    @RequestMapping(value = "/getAccess", method = RequestMethod.GET)
    public Result<User> getAccess(User user, RedisScoreValue redisScoreValue, @RequestParam("token") String token) throws Exception {
        //业务逻辑处理都可以用GlobalException处理，
        // 直接 throw new GlobalException(CodeMsg.SESSION_ERROR)即可，直接返回给前端或者客户端错误信息
        //拦截器里面也可以使用此方法
        if (user != null && redisScoreValue != null) {
            user.setAge(28);
        } else {
            throw new GlobalException(CodeMsg.MOBILE_ERROR);
            //return "登录超时了";
        }
        return Result.success(user);

    }

    //利用@Valid注解，对传入参数进行校验 ，使用的是这个  <artifactId>spring-boot-starter-validation</artifactId>，现在已经成为了标准。
    @RequestMapping(value = "/logIn")
    @Servicelock
    @ResponseBody
    public Result<String> logIn(HttpServletResponse response,  //下面要注意，先获取值，再加@Valid
                                @Valid @RequestBody UserLogin loginVo) throws Exception {
        UserLogin obj = loginVo;
        addCookie(response, "123456789");
        return Result.success("登录成功");
    }

    //利用@Valid注解，对传入参数进行校验 ，使用的是这个  <artifactId>spring-boot-starter-validation</artifactId>，现在已经成为了标准。
    @RequestMapping(value = "/connectionSocket")
    //@ResponseBody
    public String connectionSocket(Model model, HttpServletResponse response) throws Exception {
        model.addAttribute("name", "jason");
        addCookie(response, "123456789");
        return "index";
    }

    //添加cookie
    private void addCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("token", token);
        cookie.setMaxAge(20000);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @Servicelock   //实现分布式锁功能，主要采用了Lock,reentrantLock(true) 公平锁
    @RequestMapping(value = "/sendWebsocketMsg", method = RequestMethod.GET)
    @ResponseBody
    public Result<String> sendWebsocketMsg() throws Exception {
        //如果不指定userId就全部用户都发送
        WebSocketServer.sendInfo("hello,jason", 0, 0);//推送给前台
        return Result.success("发送成功");

    }

    @RequestMapping(value = "/sendUrl", method = RequestMethod.GET)
    @ResponseBody
    public String sendUrl() throws Exception {

        HttpClient httpClient = new DefaultHttpClient();
        HttpPost method = new HttpPost("http://localhost:8080/getNewsListNewest");
        method.addHeader("Content-type", "application/x-gzip");
        //method.addHeader("Content-type","application/json");
        method.setHeader("Accept", "application/json");
        method.setHeader("Content-Encoding", "gzip");
        //method.setHeader("Allow", "GET, POST,HEAD");
        method.setEntity(new StringEntity(compress("adfafafa"), Charset.forName("ISO-8859-1")));

        HttpResponse response = httpClient.execute(method);

        int statusCode = response.getStatusLine().getStatusCode();


        // Read the response body
        String body = response.getEntity().toString();
        System.out.println(body);
        return body;
    }

    public static String compress(String str) throws IOException {
        if (str == null || str.length() == 0) {
            return str;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(str.getBytes());
        gzip.close();
        return out.toString("ISO-8859-1"); // UTF-8 ISO-8859-1
    }


    //分布式锁的应用，多台负载，保证数据一致性
    @RequestMapping(value = "/distributted")
    @ResponseBody
    public String distributted() throws InterruptedException {
        String lockKey = "testRedisson";//分布式锁的key
        //初始化数据
        //objgirlInfoRedisDao.saveStock("StockCount",100);
        //执行的业务代码
        for (int i = 0; i < 55; i++) {
            RLock lock = RedissLockUtil.lock(lockKey,60);
            int stock = objgirlInfoRedisDao.readStockCount("StockCount");
            if (stock > 0) {
                objgirlInfoRedisDao.saveStock("StockCount",stock - 1);
                System.out.println("test1_:lockkey:" + lockKey + ",stock:" + (stock - 1) + "");
            }
            lock.unlock(); //释放锁
            Thread.sleep(1000);
        }
        return "Success";
    }
}
