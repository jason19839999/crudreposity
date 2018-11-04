package datacenter.crudreposity.dao.mongodb;

import datacenter.crudreposity.entity.mongodb.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @描述
 * @创建人 shicong.zhang
 * @创建时间 $date$
 * @修改人和其它信息
 */
@Repository
public interface UserServiceMongodb {
    void saveUser(User user);
    User findUserByUserName(String userName, int age);
    public void updateUser(User user);
    public void deleteUserById(Long id);
    List<User> findByName(String userName, int age);
}
