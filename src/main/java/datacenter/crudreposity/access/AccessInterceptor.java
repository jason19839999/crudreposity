package datacenter.crudreposity.access;

import java.io.OutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import datacenter.crudreposity.entity.mongodb.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSON;

@Service
public class AccessInterceptor  extends HandlerInterceptorAdapter{


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        //return ture 的意思就是放行，让进入这个接口
        //return false 的意思就是不放行，不满足进入这个接口的条件

        //只要WebMvcConfigurer注入此方法了，那么就会走这块
        if(handler instanceof HandlerMethod) {
            //获取接口传过来的token值，
            String token = request.getParameter("token");
            //获取cookie值,注意：这个是用户登录的时候保存的cookie值
            String cookie= getCookieValue(request,"token");
            //用户合法性判断
            if(token == null || cookie == null){
                //跳转到登录页面，这里没写，用return false代替了
                request.getRequestDispatcher("/NoAuthority").forward(request,response);
                //render(response,"登录超时，请重新登录");
                return false;
            }else{
                User user = new User();
                user.setId(1);
                user.setName("jason[AccessInterceptor]");
                user.setAge(18);
                //同一个线程使用的，由于每个请求就是一个单独的线程
                UserContext.setUser(user);
            }

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
