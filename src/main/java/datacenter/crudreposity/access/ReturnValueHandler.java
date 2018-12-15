package datacenter.crudreposity.access;

import datacenter.crudreposity.entity.mongodb.User;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

@Service
public class ReturnValueHandler implements HandlerMethodReturnValueHandler {

    @Override
    public   boolean supportsReturnType(MethodParameter var1){
        //获取当前参数的返回值类型，和User比较，如果相同则处理
        return User.class.equals(var1.getMethod().getReturnType());
    }

    @Override
    public void handleReturnValue(@Nullable Object var1, MethodParameter var2, ModelAndViewContainer var3, NativeWebRequest var4) throws Exception{


    }
}
