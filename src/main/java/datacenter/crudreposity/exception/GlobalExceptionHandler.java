package datacenter.crudreposity.exception;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import datacenter.crudreposity.entity.responseParam.CodeMsg;
import datacenter.crudreposity.entity.responseParam.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value=Exception.class)
//    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public Object exceptionHandler(HttpServletRequest request, Exception e){
        e.printStackTrace();

        //如果异常存在ajax请求和web端页面的，加个是否是ajax请求即可。代码如下类：
        if(isAjax(request)){
            if(e instanceof GlobalException) {
                GlobalException ex = (GlobalException)e;
                return Result.error(ex.getCm());
            }else{
                return Result.error(CodeMsg.SERVER_ERROR);
            }
        }else{
            if(e instanceof GlobalException) {
                GlobalException ex = (GlobalException)e;
                ModelAndView mv = new ModelAndView();
                mv.addObject("exception",ex.getCm().getMsg());
                mv.addObject("url",request.getRequestURI());
                mv.setViewName("error");
                return mv;
            }else{
                ModelAndView mv = new ModelAndView();
                mv.addObject("exception","服务端异常");
                mv.addObject("url",request.getRequestURI());
                mv.setViewName("error");
                return mv;
            }
        }
//        if(e instanceof GlobalException) {
//            GlobalException ex = (GlobalException)e;
//            return Result.error(ex.getCm());
//        }else if(e instanceof BindException) {
//            BindException ex = (BindException)e;
//            List<ObjectError> errors = ex.getAllErrors();
//            ObjectError error = errors.get(1);  //手机号码格式错误 :获取@interface IsMobile里面的信息
//            String msg = error.getDefaultMessage();
//            return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
//        }else if(e instanceof MethodArgumentNotValidException) {
//            MethodArgumentNotValidException ex = (MethodArgumentNotValidException)e;
//            List<ObjectError> errors =ex.getBindingResult().getAllErrors();
//            ObjectError error;
//            if(errors.size() > 0){
//                 error = errors.get(0);  //手机号码格式错误 :获取@interface IsMobile里面的信息
//            }else{
//                error = new ObjectError("","未知错误！");
//            }
//            String msg = error.getDefaultMessage();
//            return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
//        }else {
//            return Result.error(CodeMsg.SERVER_ERROR);
//        }
    }

    private static boolean isAjax(HttpServletRequest httpServletRequest){
        return httpServletRequest.getHeader("X-Requested-Width")!= null &&
                "XMLHttpRequest".equals(httpServletRequest.getHeader("X-Requested-Width").toString());
    }

//    全局拦截还可以这么写
//    @ExceptionHandler(Throwable.class)
//    @ResponseBody
//    private ResponseEntity<String>  onException(Throwable throwable){
//        return ResponseEntity.ok(throwable.getMessage());
//    }

}
