package datacenter.crudreposity.access;


import datacenter.crudreposity.entity.RedisScoreValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Service
public class RedisScoreValueArgumentResolver implements HandlerMethodArgumentResolver {
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> clazz = parameter.getParameterType();
        return clazz==RedisScoreValue.class;
    }

    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        //在这同一处理用户相关信息
        RedisScoreValue objRedisScoreValue = new RedisScoreValue();
        objRedisScoreValue.setScore(10000);
        objRedisScoreValue.setValue("滴滴事件让互联网化深思");
        return  objRedisScoreValue;
    }

}
