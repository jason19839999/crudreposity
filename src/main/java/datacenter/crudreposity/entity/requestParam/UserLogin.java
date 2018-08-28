package datacenter.crudreposity.entity.requestParam;

import datacenter.crudreposity.validator.IsMobile;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

/**
 * @描述
 * @创建人 shicong.zhang
 * @创建时间 $date$
 * @修改人和其它信息
 */
public class UserLogin {

    @IsMobile
    @NotNull
    @Length(max=11)
    private String loginName ;

    @NotNull
    @Length(min=32)
    private String pwd;


    public UserLogin() {

    }



}
