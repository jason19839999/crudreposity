package datacenter.crudreposity.aspect;

import datacenter.crudreposity.entity.responseParam.CodeMsg;
import datacenter.crudreposity.entity.responseParam.Result;
import datacenter.crudreposity.exception.GlobalException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(2)
public class AccessLimitNewAspect {

    //② 限流也可以在这里实现，如果在1分钟内访问次数超过了30次，那么就抛出异常，提示”访问太频繁！“
    @Before(value = "@annotation(datacenter.crudreposity.aspect.AccessLimitNew)")
    public void before() {
//        public Result<String> before() {
        int i = 0;
        //处理访问次数超出限制逻辑
        if (false) {
            throw new GlobalException(CodeMsg.ACCESS_LIMIT_REACHED);
//            return Result.error(CodeMsg.ACCESS_LIMIT_REACHED);
        }
//        return Result.success("登录成功");
    }

}
