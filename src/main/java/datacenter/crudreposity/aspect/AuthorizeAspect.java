package datacenter.crudreposity.aspect;

import datacenter.crudreposity.access.UserContext;
import datacenter.crudreposity.entity.mongodb.User;
import datacenter.crudreposity.entity.responseParam.CodeMsg;
import datacenter.crudreposity.exception.GlobalException;
import org.aspectj.lang.annotation.*;
import org.hibernate.sql.OracleJoinFragment;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Order(1)
public class AuthorizeAspect {

    @Pointcut("execution(public * datacenter.crudreposity.action.*.*(..))"+
            "&& !execution(public * datacenter.crudreposity.action.UserController.*(..))")
    public void verify(){}

    //① 用户身份验证
    @Before("verify()")
    public void doVerify() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //获取token，如果token验证通过，那么就放行，否则throw  new  GlobalException......
        String token = request.getParameter("token");
        if(false) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }else{
            //这里不能给UserContext设置用户信息，由于UserContext是线程本地变量，它是和线程绑定一起的，为线程提供一个本地变量副本。
            //由于切面编程和请求业务是分开的，不是同一个线程所以UserContext这个变量不存在。
            //在TokenFilter → OncePerRequestFilter 或者Interceptor过滤器里面就可以，因为他们在同一个线程内，这个已经实现了。。。
//            User user = new User();
//            user.setId("1");
//            user.setName("jason[AccessInterceptor]" + "///" +"AuthorizeAspect");
//            user.setAge(18);
            try {
//                UserContext.setUser(user);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    //方法执行完最后执行，此处理，在ServicelockAspect.Around切面编程之后执行
    @AfterReturning(returning = "object", pointcut = "verify()")
    public  void doAfterReturning(Object object){
        Object obj = object;

    }

//    @Around("verify")
//    public void around(){
//
//    }
//
//    @After("verify")
//    public void after(){
//
//    }
//
//    @AfterThrowing("verify")
//    public void afterThrowing(){
//
//    }


}
