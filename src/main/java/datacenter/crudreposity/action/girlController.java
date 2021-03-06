package datacenter.crudreposity.action;

import com.alibaba.fastjson.JSONObject;
import datacenter.crudreposity.aspect.AccessLimitNew;
import datacenter.crudreposity.aspect.Servicelock;
import datacenter.crudreposity.auto_configure.HelloWorldConfiguration;
import datacenter.crudreposity.config.MybatisSessionFactory;
import datacenter.crudreposity.dao.mongodb.UserServiceMongodb;
import datacenter.crudreposity.dao.mybatis.HKBillsDao;
import datacenter.crudreposity.dao.mysql2.UserMysqlRepository;
import datacenter.crudreposity.dao.redis.girlInfoRedisDao;
import datacenter.crudreposity.aapractise.distributedlock.redis.RedissLockUtil;
import datacenter.crudreposity.entity.*;
import datacenter.crudreposity.entity.mongodb.User;
import datacenter.crudreposity.entity.requestParam.UserLogin;
import datacenter.crudreposity.entity.responseParam.CodeMsg;
import datacenter.crudreposity.entity.responseParam.Result;
import datacenter.crudreposity.exception.GlobalException;
import datacenter.crudreposity.rabbitmq.MQSender;
import datacenter.crudreposity.service.girlInfoDealService;
import datacenter.crudreposity.util.ExportUtil;
import datacenter.crudreposity.websocket.WebSocketServer;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.zip.GZIPOutputStream;

import org.redisson.api.RLock;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.TimeUnit;


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
    private UserServiceMongodb userServiceMongodb;

//    @Autowired
//    private MQSender mQSender;
//    private static final Logger logger = LoggerFactory.getLogger(girlController.class);

    @Autowired
    private HelloWorldConfiguration helloWorldConfiguration;

    @Autowired
    private ApplicationContext applicationContext;

    private static final Logger logger = LogManager.getLogger(girlController.class);

    //模拟登录页面，如下配置即可
    @RequestMapping(value = "/")
//    @RequestBusinessLog(name="产品对比搜索API",code = "productVsSearchAPI")
    public String connectSocket(Model model, HttpServletResponse response) throws Exception {
        model.addAttribute("name", "jason");
        addCookie(response, "123456789");
        return "index";
    }

    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getNewsList() {
        User user = new User();
        List<User> lst = null;
        try {
//            user.setId("5bdedd367d0fb201388d4de2");
            user.setAge(30);
            user.setName("聪哥");
            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.HOUR, 8);// 24小时制
            date = cal.getTime();
            user.setCreate_date(date);
//            userServiceMongodbImpl.saveUser(user);
            lst = userServiceMongodb.findByName("聪哥",30);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        //测试msql这个连接是否通了。这里面包含了读写分离
//        List<Girlnfo> lst = objgirlInfoDealService.getAllGirls();
//        //girlInfoListResponse obj = new girlInfoListResponse(lst);
//        girlInfoListResponse obj = new girlInfoListResponse();
//        obj.setKey("小雪老婆好呀");
//        obj.setResponse_data(lst);
//
        return new ResponseEntity<List<User>>(lst, HttpStatus.OK);
    }

    @RequestMapping(value = "/getGirl", method = RequestMethod.GET)
    public ResponseEntity<List<Girlnfo>> getGirl() {
//        测试msql这个连接是否通了。这里面包含了读写分离
        List<Girlnfo> lst = objgirlInfoDealService.getAllGirls();
        //girlInfoListResponse obj = new girlInfoListResponse(lst);
        girlInfoListResponse obj = new girlInfoListResponse();
        obj.setKey("小雪老婆好呀");
        obj.setResponse_data(lst);
//
        return new ResponseEntity<List<Girlnfo>>(lst, HttpStatus.OK);
    }

    @RequestMapping(value = "getRedis", method = RequestMethod.GET)
    @ResponseBody
    public String getRedis() {
        logger.info("进入redis");
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
        if(lst != null && lst.size() > 0) {
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
        }

        objgirlInfoRedisDao.saveHash("set_hash", "name", "hello,jason,goodafternoon");
        objgirlInfoRedisDao.saveHash("set_hash", "age", "28");
        RedisScoreValue obj = objgirlInfoRedisDao.readHash("set_hash");

        return obj.getValue() + "------" + obj.getScore();
    }

    @RequestMapping(value = "/getMybatis", method = RequestMethod.GET)
    @ResponseBody
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
    @ResponseBody  //返回strng或者json
    public String getMongodb() throws Exception {
        User objUser = new User();
        objUser.setId("1");
        objUser.setName("jason");
        objUser.setAge(18);
        //插入到数据库
        userServiceMongodb.saveUser(objUser);

        //按照名字查询
        objUser = userServiceMongodb.findUserByUserName(objUser.getName(), objUser.getAge());


        return "调用成功";
    }

    //@AccessLimit(seconds = 30)    //接口下流注入 比如：每分钟只能请求60次。思路：每次请求count放入redis,然后设置redis过期时间为1分钟，判断一分钟内不能超过60次。一分钟后过期重新计算
    @AccessLimitNew
//    @Servicelock   //实现分布式锁功能，主要采用了Lock,reentrantLock(true) 公平锁
    @RequestMapping(value = "/getAccess")
    @ResponseBody
    public Result<User> getAccess(User user) throws Exception {
        //业务逻辑处理都可以用GlobalException处理，
        // 直接 throw new GlobalException(CodeMsg.SESSION_ERROR)即可，直接返回给前端或者客户端错误信息
       //在此只要处理业务逻辑即可。。。
        if (user != null) {
//            user = new User();
//            user.setId("1");
//            user.setName("congcong");
//            user.setAge(28);
        } else {
            //在这里throw  公共异常处理捕捉不到,
            //  ※※ ※※※※※※※ →现在可以捕捉到了，原因 是之前方法头设置了Servicelock注解。那么程序在Around切面环绕着，所以导致在这里抛出异常ControllerAdvice捕捉不到
            //记录异常日志
            throw new GlobalException(CodeMsg.SESSION_ERROR);
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
        addCookie(response, "token---123456789");
        return Result.success("登录成功");
    }

    @RequestMapping(value = "/denglu")
    @Servicelock(name = "jason zhang",description = "18岁")
    @AccessLimitNew(count = 5)
    @ResponseBody
    public Result<Object> denglu(HttpServletResponse response, @RequestParam("token") String token,User user) throws Exception {
        String name = user.getName();
        addCookie(response, token);
        if(true){
            //在这里throw  公共异常处理捕捉不到,
            //  ※※ ※※※※※※※ →现在可以捕捉到了，原因 是之前方法头设置了Servicelock注解。那么程序在Around切面环绕着，所以导致在这里抛出的异常ControllerAdvice捕捉不到
            //  现在的做法是在这里返回异常信息给Servicelock.Around ，在Servicelock.Around里面抛出异常。。。
           // throw new GlobalException(CodeMsg.SESSION_ERROR);  //这里的异常一般都是业务处理的异常了。。。
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }
        return Result.success(user);
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
        //这里的message和string相互转化.参照:    /datacenter/crudreposity/dao/redis/Impl/girlInfoRedisDaoImpl.java的beanToString，stringToBean
        //或者list<>和string相互转化也可以参照：/datacenter/crudreposity/util/JsonUtils.java
//        WebSocketServer.sendInfo("hello,jason", 0, 0);//推送给前台
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
        objgirlInfoRedisDao.saveStock("StockCount", 100);
        //执行的业务代码
        for (int i = 0; i < 55; i++) {
            //设置锁的过期时间
            RLock lock = RedissLockUtil.lock(lockKey, TimeUnit.MILLISECONDS, 200);
            int stock = objgirlInfoRedisDao.readStockCount("StockCount");
            if (stock > 0) {
                objgirlInfoRedisDao.saveStock("StockCount", stock - 1);
                System.out.println("test1_:lockkey:" + lockKey + ",stock:" + (stock - 1) + "");
            }
            lock.unlock(); //释放锁
            Thread.sleep(1000);
        }
        return "Success";
    }

    @RequestMapping(value = "/getConectors")
    @ResponseBody
    public String getConectors() throws InterruptedException {

        String result = "";
        CopyOnWriteArraySet<WebSocketServer> list = WebSocketServer.webSocketSet;
        for (WebSocketServer item : list) {
            result += "userId: " + item.userId + "  goodsId: " + item.goodsId + "<br>";
        }

//        logger.trace("我是trace");
//        logger.info("我是info信息");
//        logger.error("我是error");
//        logger.fatal("我是fatal");
//        logger.trace("退出程序.");

        logger.info("log4j 添加成功了！！！");
        return result;
    }

    @RequestMapping(value = "/ajax")
    public String ajax() {

        return "ajax";
    }

    @RequestMapping(value = "/sendRabbitMq")
    @ResponseBody
    public String sendRabbitMq() throws InterruptedException {
        String msg = "Hello,Jason !";
//        mQSender.sendMiaoshaMessage(msg);
        return msg;
    }

    @RequestMapping(value = "/testAutoConfig")
    @ResponseBody
    public String testAutoConfig(){
        String result ="NO";
//     通过ApplicationContext的调用
        result = applicationContext.getBean(HelloWorldConfiguration.class).helloWorld();
//        通过Autowired的调用
//        result = helloWorldConfiguration.helloWorld();
        return result +"---" + new Date();
    }

    @GetMapping("/hello-world")
    @ResponseBody
    public DeferredResult<String> helloWorld() {
        DeferredResult<String> result = new DeferredResult<>(50L);
//        result.setResult("Hello,World");
        println("Hello,World");
        result.onCompletion(() -> {
            println("执行结束");
        });

        result.onTimeout(() -> {
            println("执行超时");
        });

        return result;
    }

    @GetMapping("/completion-stage")
    @ResponseBody
    public CompletionStage<String> completionStage(){
        final long startTime = System.currentTimeMillis();

        println("Hello,World");

        return CompletableFuture.supplyAsync(()->{
            long costTime = System.currentTimeMillis() - startTime;
            println("执行计算结果，消耗：" + costTime + " ms.");
            return "Hello,World"; // 异步执行结果
        });
    }

    @GetMapping("/callable-hello-world")
    @ResponseBody
    public Callable<String> callableHelloWorld() {
        final long startTime = System.currentTimeMillis();

        println("Hello,World");

        return () -> {
            long costTime = System.currentTimeMillis() - startTime;
            println("执行计算结果，消耗：" + costTime + " ms.");
            return "Hello,World";
        };
    }

    private  void println(Object object) {
        String threadName = Thread.currentThread().getName();
        System.out.println("HelloWorldAsyncController[" + threadName + "]: " + object);
    }


    //导出excel
   @GetMapping("/file")
   public String download(HttpServletResponse response) {
       List<Map<String, Object>> dataList = null;

       List<News> lst = objgirlInfoDealService.getNews();// 查询到要导出的信息

       if (lst.size() == 0) {
           return "无数据导出";
       }
       String sTitle = "website,navigation,junior_channel,news_time,title";
       String fName = "News_";
       String mapKey = "website,navigation,junior_channel,news_time,title";
       dataList = new ArrayList<>();
       Map<String, Object> map = null;
       for (News obj : lst) {
           map = new HashMap<>();
           if(obj.getOther_info() != null && obj.getOther_info() != ""){
               JSONObject json = JSONObject.parseObject(obj.getOther_info());
               if(json.containsKey("website")){
                   map.put("website", json.getString("website"));
               }else{
                   map.put("website", "");
               }
               if(json.containsKey("navigation")){
                   map.put("navigation", json.getString("navigation"));
               }else{
                   map.put("navigation", "");
               }
           }else{
               map.put("website", "");
               map.put("navigation", "");
           }
           map.put("junior_channel", obj.getJunior_channel());
           SimpleDateFormat format0 = new SimpleDateFormat("MM-dd HH:mm");
           String time = format0.format(obj.getNews_time());
           map.put("news_time", time);
           map.put("title", obj.getTitle());

           dataList.add(map);
       }
       try (final OutputStream os = response.getOutputStream()) {
           ExportUtil.responseSetProperties(fName, response);
           ExportUtil.doExport(dataList, sTitle, mapKey, os);
           return null;
       } catch (Exception e) {
//           log.error("生成csv文件失败", e);
       }
       return "数据导出出错";
   }


}
