package datacenter.crudreposity.filters;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import datacenter.crudreposity.config.ServingSettings;
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

        //获取接口的requestBody消息体，说白了就是实体对象
        ServingRequestWrapper requestWrapper = new ServingRequestWrapper(httpServletRequest);
        String body = requestWrapper.getBody();
        String uri = requestWrapper.getRequestURI();
        JSONObject jsonObject = JSON.parseObject(body);

        try {
            String imei = jsonObject.getJSONObject("header").getString("imei");
            String keyCode = jsonObject.getJSONObject("header").getString("key_code");
            String targetUri = uri;

            if (keyCodeValid(imei,keyCode)) {
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
        if (StringUtils.isEmpty(imei) || StringUtils.isEmpty(keyCode))
            return false;
        return keyCode.equals(generateKeyCode(imei));
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
