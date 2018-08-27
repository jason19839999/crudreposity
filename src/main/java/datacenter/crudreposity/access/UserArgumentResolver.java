package datacenter.crudreposity.access;

import datacenter.crudreposity.entity.mongodb.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {


    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> clazz = parameter.getParameterType();
        return clazz==User.class;
    }

    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        //在这同一处理用户相关信息
        User user = new User();
        user.setId(1);
        user.setName("jason");
        user.setAge(18);
        return  user;
//        UserContext.setUser(user);
//        return UserContext.getUser();
    }

}
