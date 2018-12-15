package datacenter.crudreposity.access;

import java.io.OutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import datacenter.crudreposity.entity.mongodb.User;
import datacenter.crudreposity.entity.responseParam.CodeMsg;
import datacenter.crudreposity.exception.GlobalException;
import datacenter.crudreposity.filters.ServingRequestWrapper;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Service
public class AccessInterceptor  extends HandlerInterceptorAdapter{

    //此拦截器建议只用做功能上的拦截，比如限流等操作。
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        //return ture 的意思就是放行，让进入这个接口
        //return false 的意思就是不放行，不满足进入这个接口的条件

        //只要WebMvcConfigurer注入此方法了，那么就会走这块
        if(handler instanceof HandlerMethod) {
            //此拦截器适合web端方式请求，即http://localhost:8086/denglu?token=999这种方式的。。。不适合app客户端请求，app客户端用OncePerRequestFilter 的 TokenFilter。。。
            //获取接口传过来的token值，
            // ① Get方式：http://localhost:8086/denglu?token=999
//            String token = request.getParameter("token");
//             ② POST方式：参数type：JSON  不能再这里加这个读取，会报错 java.io.IOException: Stream closed，放到doFilterInternal就可以了。
//            ServingRequestWrapper requestWrapper = new ServingRequestWrapper(request);
//            String body = requestWrapper.getBody();
//            String uri = requestWrapper.getRequestURI();
//            JSONObject jsonObject = JSON.parseObject(body);
//            if(jsonObject != null && jsonObject.containsKey("token")){
//                token = jsonObject.getString("token");
//            }

            //获取用户信息，主要从cookie和redis获取
            //获取cookie值,注意：这个是用户登录的时候保存的cookie值
            // 或者读取redis的session值，由于接口过来的保存不了cookie,所有这里必须用分布式session处理
//            String cookie= getCookieValue(request,"token");
            //用户合法性判断
//            if(token == null && cookie == null){
                //通过以下三种方式处理异常逻辑信息提示

                //①跳转到登录页面或者无权限页面，这里没写，用return false代替了
                //request.getRequestDispatcher("/NoAuthority").forward(request,response);
                //return false;

                //②将错误信息输出到页面
                //render(response,"登录超时，请重新登录");
                //return false;

                //③通过统一的异常处理输出格式统一的结果给前端或者app客户端
//                throw new GlobalException(CodeMsg.SESSION_ERROR);

//            }else{  //获取用户信息，添加到ThreadLocal线程，供接口参数初始化用，addArgumentResolvers
//                User user = new User();
//                user.setId("1");
//                user.setName("jason[AccessInterceptor]" + "///" +cookie);
//                user.setAge(18);
//                //同一个线程使用的，由于每个请求就是一个单独的线程
//                UserContext.setUser(user);
//            }

            //以下是接口拦截处理，比如请求这个接口限流，即：每分钟只限制请求60次等类似的操作
            HandlerMethod hm = (HandlerMethod)handler;
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if(accessLimit == null) {
                //如果没注入就不处理了，说明没注入此拦截器
                return true;
            }else{
                //如果接口注入了，就继续处理
                int seconds = accessLimit.seconds();
                //如果不满足条件就返回false,拦截成功
            }

        }
        return true;
    }

    private String getCookieValue(HttpServletRequest request, String cookiName) {
        Cookie[]  cookies = request.getCookies();
        if(cookies == null || cookies.length <= 0){
            return null;
        }
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals(cookiName)) {
                return cookie.getValue();
            }
        }
        return null;
    }

    private void render(HttpServletResponse response, String msg)throws Exception {
        response.setContentType("application/json;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        //String str  = JSON.toJSONString(Result.error(cm));
        out.write(msg.getBytes("UTF-8"));
        out.flush();
        out.close();
    }


}
