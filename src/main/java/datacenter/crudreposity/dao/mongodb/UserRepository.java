package datacenter.crudreposity.dao.mongodb;

import datacenter.crudreposity.entity.mongodb.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @描述
 * @创建人 shicong.zhang
 * @创建时间 $date$
 * @修改人和其它信息
 */
public interface UserRepository extends MongoRepository<User, String> {

    User findUserByAgeAndName(String name,int age);


}
