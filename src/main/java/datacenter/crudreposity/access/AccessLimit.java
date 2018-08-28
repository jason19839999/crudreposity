package datacenter.crudreposity.access;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(METHOD)
public @interface AccessLimit {
    //接口注解绑定时候的参数  例如：@AccessLimit(seconds = 30) 这里模拟的就是接口限流操作，
    //return true:说明验证通过，可以继续访问此接口，return false:说明验证不通过，不允许访问此接口。
    int seconds();
 }
