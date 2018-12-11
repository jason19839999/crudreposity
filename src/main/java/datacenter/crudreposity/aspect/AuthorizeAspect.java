package datacenter.crudreposity.aspect;

import datacenter.crudreposity.entity.responseParam.CodeMsg;
import datacenter.crudreposity.exception.GlobalException;
import org.aspectj.lang.annotation.*;
import org.hibernate.sql.OracleJoinFragment;
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
        //获取token，如果token验证通过，那么就放行，否则throw  new  GlobalException......
        String token = request.getParameter("token");
        if(false) {
            throw new GlobalException(CodeMsg.SESSION_ERROR);
        }

    }

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
