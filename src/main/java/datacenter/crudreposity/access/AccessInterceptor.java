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
        //只要WebMvcConfigurer注入此方法了，那么就会走这块
        if(handler instanceof HandlerMethod) {
            User user = new User();
            user.setId(1);
            user.setName("jason[AccessInterceptor]");
            user.setAge(18);
            UserContext.setUser(user);

            HandlerMethod hm = (HandlerMethod)handler;
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if(accessLimit == null) {
                //如果没注入就不处理了
                return true;
            }else{
                //如果接口注入了，就继续处理
                int seconds = accessLimit.seconds();
            }

        }
        return true;
    }



}
