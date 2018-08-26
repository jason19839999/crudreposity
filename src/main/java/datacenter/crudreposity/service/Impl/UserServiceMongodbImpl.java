package datacenter.crudreposity.service.Impl;

import datacenter.crudreposity.dao.mongodb.UserRepository;
import datacenter.crudreposity.entity.mongodb.User;
import datacenter.crudreposity.service.UserServiceMongodb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @描述
 * @创建人 shicong.zhang
 * @创建时间 $date$
 * @修改人和其它信息
 */
@Service
public class UserServiceMongodbImpl implements UserServiceMongodb {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByName(String name) {
        return userRepository.findByName(name);
    }
}
