package datacenter.crudreposity.service;

import datacenter.crudreposity.entity.mongodb.User;
import org.springframework.stereotype.Repository;

/**
 * @描述
 * @创建人 shicong.zhang
 * @创建时间 $date$
 * @修改人和其它信息
 */
@Repository
public interface UserServiceMongodb {
    void saveUser(User user);
    User findUserByUserName(String userName);
    public void updateUser(User user);
    public void deleteUserById(Long id);

}
