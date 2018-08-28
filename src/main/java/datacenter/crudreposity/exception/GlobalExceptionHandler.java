package datacenter.crudreposity.exception;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import datacenter.crudreposity.entity.responseParam.CodeMsg;
import datacenter.crudreposity.entity.responseParam.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    @ExceptionHandler(value=Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest request, Exception e){
        e.printStackTrace();
        if(e instanceof GlobalException) {
            GlobalException ex = (GlobalException)e;
            return Result.error(ex.getCm());
        }else if(e instanceof BindException) {
            BindException ex = (BindException)e;
            List<ObjectError> errors = ex.getAllErrors();
            ObjectError error = errors.get(1);  //手机号码格式错误 :获取@interface IsMobile里面的信息
            String msg = error.getDefaultMessage();
            return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
        }else if(e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException)e;
            List<ObjectError> errors =ex.getBindingResult().getAllErrors();
            ObjectError error;
            if(errors.size() > 0){
                 error = errors.get(0);  //手机号码格式错误 :获取@interface IsMobile里面的信息
            }else{
                error = new ObjectError("","未知错误！");
            }
            String msg = error.getDefaultMessage();
            return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
        }else {
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }
}
