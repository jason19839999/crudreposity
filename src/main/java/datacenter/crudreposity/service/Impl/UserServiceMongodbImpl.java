package datacenter.crudreposity.service.Impl;

import com.mongodb.BasicDBObject;
import com.mongodb.QueryBuilder;
import datacenter.crudreposity.dao.mongodb.UserRepository;
import datacenter.crudreposity.entity.mongodb.User;
import datacenter.crudreposity.service.UserServiceMongodb;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import java.util.List;

//https://blog.csdn.net/zhuchunyan_aijia/article/details/80298529  参照学习地址

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

    @Autowired
    MongoTemplate mongoTemplate;
    private Query query;

    /**
     * 创建对象
     * @param user
     */
    public void saveUser(User user) {
        mongoTemplate.save(user);
    }

    /**
     * 根据用户名查询对象
     * @param userName
     * @return
     */
    @Override
    public User findUserByUserName(String userName,int age) {
        Query query=new Query();
        Criteria criteria = new Criteria();
        //查询且语句：a && b
        query.addCriteria(Criteria.where("userName").is(userName));
        query.addCriteria(Criteria.where("age").is(age));
        User user =  mongoTemplate.findOne(query , User.class);
        //查询且语句：a && b   或者如下
        criteria.and("userName").is(userName);
        criteria.and("age").is(age);
        query = new Query(criteria);
        user =  mongoTemplate.findOne(query , User.class);

        //查询或语句：a || b
        criteria.orOperator(Criteria.where("userName").is(userName),Criteria.where("age").is(age));
        query = new Query(criteria);
        user =  mongoTemplate.findOne(query , User.class);

        //where a && b && (a || b)
        Criteria criteriaAnd = new Criteria();
        criteriaAnd.and("userName").is(userName);
        criteriaAnd.and("age").is(age);

        Criteria criteriaOr = new Criteria();
        criteriaOr.orOperator(Criteria.where("userName").is(userName),Criteria.where("age").is(age));

        query.addCriteria(criteriaAnd);
        query.addCriteria(criteriaOr);
        user =  mongoTemplate.findOne(query , User.class);

        //按照年龄排序，并且实现分页
        query.limit(10 - 1).skip(0).with(new Sort(new Sort.Order(Sort.Direction.DESC, "age")));
        List<User> userList =  mongoTemplate.find(query , User.class);

        return user;
    }

    /**
     * 更新对象
     * @param user
     */
    @Override
    public void updateUser(User user) {
        Query query=new Query(Criteria.where("id").is(user.getId()));
        Update update= new Update().set("userName", user.getName()).set("age", user.getAge());
        //更新查询返回结果集的第一条
        mongoTemplate.updateFirst(query,update,User.class);
        //更新查询返回结果集的所有
        mongoTemplate.updateMulti(query,update,User.class);
    }

    /**
     * 删除对象
     * @param id
     */
    @Override
    public void deleteUserById(Long id) {
        Query query=new Query(Criteria.where("id").is(id));
        mongoTemplate.remove(query,User.class);
    }



}
