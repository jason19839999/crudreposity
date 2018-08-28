package datacenter.crudreposity.aspect;
import java.lang.annotation.*;


//添加lock 注解


@Target({ElementType.PARAMETER, ElementType.METHOD})    
@Retention(RetentionPolicy.RUNTIME)    
@Documented    
public  @interface Servicelock { 
	 String description()  default "";
}
