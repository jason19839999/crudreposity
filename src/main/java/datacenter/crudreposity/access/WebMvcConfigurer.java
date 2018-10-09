package datacenter.crudreposity.access;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;
//WebMvcConfigurerAdapter 在高版本替换方案
//方案一： extends WebMvcConfigurationSupport
//方案二：implements WebMvcConfigurer

@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {
    @Autowired
    private UserArgumentResolver userArgumentResolver;

    @Autowired
    private RedisScoreValueArgumentResolver redisScoreValueArgumentResolver;

    @Autowired
    private AccessInterceptor accessInterceptor;

    @Autowired
    ReturnValueHandler returnValueHandler;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(userArgumentResolver);
        argumentResolvers.add(redisScoreValueArgumentResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注意：这里千万注意   登录页面和错误页面    如果是接口的话，登录接口，退出接口，错误返回接口不要拦截
        //registry.addInterceptor(accessInterceptor).excludePathPatterns("/logIn","/NoAuthority","/connectionSocket","/sendWebsocketMsg","/sendUrl","/distributted","/getConectors","/denglu","/ajax");
        //addPathPatterns("/**")对所有请求都拦截，但是排除了/toLogin和/login请求的拦截
        //registry.addInterceptor(accessInterceptor).addPathPatterns("/**").excludePathPatterns("/toLogin","/login");
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        returnValueHandlers.add(returnValueHandler);
    }


}
