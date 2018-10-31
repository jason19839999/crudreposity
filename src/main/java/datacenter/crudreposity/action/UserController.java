package datacenter.crudreposity.action;


import datacenter.crudreposity.entity.Girlnfo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/users")
public class UserController {

    @ApiOperation(value="创建用户", notes="根据User对象创建用户")
    @ApiImplicitParam(name = "Girlnfo", value = "用户详细实体user", required = true, dataType = "Girlnfo")
    @RequestMapping(value="postUser", method=RequestMethod.POST)
    public String postUser(@RequestBody Girlnfo girl) {

        return "success" + "---" + girl.getCup_size();
    }

    @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Long")
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public String getUser(@PathVariable("id") Long id) {
        return "成功了---" + id;
    }



}
