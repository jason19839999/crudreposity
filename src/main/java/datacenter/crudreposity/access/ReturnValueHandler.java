package datacenter.crudreposity.access;

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

        return  true;
    }

    @Override
    public void handleReturnValue(@Nullable Object var1, MethodParameter var2, ModelAndViewContainer var3, NativeWebRequest var4) throws Exception{


    }
}
