package datacenter.crudreposity.aspect;

import datacenter.crudreposity.entity.responseParam.CodeMsg;
import datacenter.crudreposity.exception.GlobalException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class AuthorizeAspect {

    @Pointcut("execution(public * datacenter.crudreposity.action.*.*(..))"+
            "&& !execution(public * datacenter.crudreposity.action.UserController.*(..))")
    public void verify(){}

    @Before("verify()")
    public void doVerify() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

//        throw new GlobalException(CodeMsg.SESSION_ERROR);

    }




}
