package datacenter.crudreposity.dao.redis.Impl;

import datacenter.crudreposity.dao.redis.girlInfoRedisDao;
import datacenter.crudreposity.entity.Girlnfo;
/**
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
**/
/**
 * @描述
 * @创建人 shicong.zhang
 * @创建时间 $date$
 * @修改人和其它信息
 */
public class girlInfoRedisDaoImpl implements girlInfoRedisDao {
    //@Autowired
    //private RedisTemplate<Serializable, Serializable> redisTemplate;

    @Override
    public void save(final Girlnfo obj) {
       /** redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection redisConnection)
                    throws DataAccessException {
               // for (int nid : obj.getId()) {
                    redisConnection.zAdd(
                            redisTemplate.getStringSerializer().serialize(
                                    Integer.toString(obj.getId())),  //唯一标识
                            234297238,                            //时间戳
                            redisTemplate.getStringSerializer().serialize(
                                    Integer.toString(obj.getAge())));  //具体值
               // }
                return null;
            }
        });
        **/
    }

    @Override
    public Girlnfo read(final String id) {
       /** return redisTemplate.execute(new RedisCallback<Girlnfo>() {
            @Override
            public Girlnfo doInRedis(RedisConnection redisConnection)
                    throws DataAccessException {
                byte[] key = redisTemplate.getStringSerializer().serialize(id);
                if (redisConnection.exists(key)) {
                    Set<byte[]> originNids = redisConnection.zRange(key, 0, -1);
                    Girlnfo history = new Girlnfo();
                    List<Integer> nids = new ArrayList<Integer>();

                    for (byte[] o : originNids) {
                        nids.add(Integer.valueOf(redisTemplate
                                .getStringSerializer().deserialize(o)));
                    }
                    history.setAge(0);
                    return history;
                }
                return null;
            }
        });  **/
       return  null;
    }

}
