package datacenter.crudreposity.entity.requestParam;

import datacenter.crudreposity.validator.IsMobile;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @描述
 * @创建人 shicong.zhang
 * @创建时间 $date$
 * @修改人和其它信息
 */
public class UserLogin {

    @Length(max = 20)    //这里注解的顺序决定了GlobalException处理中错误信息的顺序11
    @IsMobile
    @NotNull
    private String loginName;

    @NotNull
    @Length(min = 32)
    private String pwd;


    public UserLogin() {

    }


    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
