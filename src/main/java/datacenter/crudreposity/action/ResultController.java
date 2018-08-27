package datacenter.crudreposity.action;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResultController {
    @RequestMapping(value = "/NoAuthority")
    public String NoAuthority() {
        return "登录超时，请重新登录";
    }
}
