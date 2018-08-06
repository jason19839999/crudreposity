package datacenter.crudreposity.dao.redis.Impl;

import datacenter.crudreposity.dao.redis.girlInfoRedisDao;
import datacenter.crudreposity.util.ObjectTranscoder;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.Serializable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Service;

/**
 * @描述
 * @创建人 shicong.zhang
 * @创建时间 $date$
 * @修改人和其它信息
 */
@Service
public class girlInfoRedisDaoImpl implements girlInfoRedisDao {
    @Autowired
    private RedisTemplate<Serializable, Serializable> redisTemplate;

    //说明 util里面的ObjectTranscoder，负责序列化value和反序列化value,他序列化的可以是Object,其中Object可以是List<Object>,
    //String等等类型的value,
    // 例如： 保存  → ObjectTranscoder.serialize(lst);
    //        获取  →  List<NewStockResponseData> lst = (List<NewStockResponseData>)ObjectTranscoder.deserialize(in);

    @Override
    public void save() {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection redisConnection)
                    throws DataAccessException {
              redisConnection.set(redisTemplate.getStringSerializer().serialize("name"),
                      ObjectTranscoder.serialize("test2018-08-06 OK? "),
                      Expiration.seconds(300000),
                      RedisStringCommands.SetOption.UPSERT);
                return null;
            }
        });

    }

    @Override
    public String read(final String id) {
       return redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection redisConnection)
                    throws DataAccessException {
                byte[] in = redisConnection.get(redisTemplate.getStringSerializer().serialize("name"));
                return ObjectTranscoder.deserialize(in).toString();
            }
        });

    }

}
