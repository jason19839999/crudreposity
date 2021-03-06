package datacenter.crudreposity.filters;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import datacenter.crudreposity.CacheCommandLineRunner;
import datacenter.crudreposity.access.UserContext;
import datacenter.crudreposity.config.ServingSettings;
import datacenter.crudreposity.entity.mongodb.User;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;

public class TokenFilter extends OncePerRequestFilter {
    private static Logger logger = LoggerFactory.getLogger(TokenFilter.class);

    private static final String KEY_CODE_PRE_CONFUSION = "BeijingMiningTechnology";
    private static final String KEY_CODE_END_CONFUSION = "ZunjiaStock";

    @Autowired
    private ServingSettings servingSettings;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        //girlController 的 denglu接口，这里只是个例子，登录之后会返回token,那么其他接口请求会带着这个token；
        // ① Get方式：http://localhost:8086/denglu?token=999
        String token = httpServletRequest.getParameter("token");
         if(token != null && !token.equals("")){
             //这里通过token获取用户信息；这里没写读取redis用户信息的，所以随便创建了一个对象模拟用户登录
             User user = new User();
             user.setId("1");
             user.setName("jason[AccessInterceptor]" + "///" +"TokenFilter");
             user.setAge(18);
             UserContext.setUser(user);
         }
        // ② POST方式：参数type：JSON
        //获取接口的requestBody消息体，说白了就是实体对象
        ServingRequestWrapper requestWrapper = new ServingRequestWrapper(httpServletRequest);
        String body = requestWrapper.getBody();
        String uri = requestWrapper.getRequestURI();
        if(body!=null && !body.equals("")){
            JSONObject jsonObject = JSON.parseObject(body);
            token = jsonObject.getString("token");
            //这里通过token获取用户信息；这里没写读取redis用户信息的，所以随便创建了一个对象模拟用户登录
            User user = new User();
            user.setId("1");
            user.setName("jason[AccessInterceptor]" + "///" +"TokenFilter");
            user.setAge(18);
            UserContext.setUser(user);
        }
        try {
            String imei ="";// jsonObject.getJSONObject("header").getString("imei");
            String keyCode =""; // jsonObject.getJSONObject("header").getString("key_code");
            String targetUri = uri;

            //CacheCommandLineRunner.URIS程序启动时预加载所有类  接口鉴权判断合法请求
            if (CacheCommandLineRunner.URIS.contains(uri) && keyCodeValid(imei,keyCode)) {
                requestWrapper.getRequestDispatcher(targetUri).forward(requestWrapper, httpServletResponse);
            }
            else {
                targetUri = "/NoAuthority";
                requestWrapper.getRequestDispatcher(targetUri).forward(requestWrapper, httpServletResponse);
           }

        } catch (Exception ex) {
            logger.info(ex.getMessage());
            filterChain.doFilter(requestWrapper, httpServletResponse);
        }
    }


    public static boolean keyCodeValid(String imei, String keyCode) {
        return true;
//        if (StringUtils.isEmpty(imei) || StringUtils.isEmpty(keyCode))
//            return false;
//        return keyCode.equals(generateKeyCode(imei));
    }

    public static String generateKeyCode(String imei) {
        return MD5(KEY_CODE_PRE_CONFUSION + imei + KEY_CODE_END_CONFUSION);
    }

    public final static String MD5(String s) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
