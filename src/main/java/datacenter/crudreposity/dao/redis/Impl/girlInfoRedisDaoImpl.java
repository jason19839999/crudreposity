package datacenter.crudreposity.dao.redis.Impl;

import datacenter.crudreposity.dao.redis.girlInfoRedisDao;
import datacenter.crudreposity.entity.RedisScoreValue;
import datacenter.crudreposity.util.ObjectTranscoder;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.RedisZSetCommands;
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
    public void save(String key) {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection redisConnection)
                    throws DataAccessException {
              /**redisConnection.set(redisTemplate.getStringSerializer().serialize(key),
                      ObjectTranscoder.serialize("test2018-08-06 OK? 大学教授来了 @#@￥@#￥#%"),
                      Expiration.seconds(300000),
                      RedisStringCommands.SetOption.UPSERT); **/

                redisConnection.set(redisTemplate.getStringSerializer().serialize(key), ObjectTranscoder.serialize("test2018-08-06 OK? Yes Or No @#@￥@#￥#%"));
                //redisConnection.zAdd()

                return null;
            }
        });

    }

    @Override
    public String read(final String key) {
       return redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection redisConnection)
                    throws DataAccessException {
                byte[] in = redisConnection.get(redisTemplate.getStringSerializer().serialize(key));
                return ObjectTranscoder.deserialize(in).toString();
            }
        });

    }

    @Override
    public List<Integer> readSetIntList(final String uid) {
        return redisTemplate.execute(new RedisCallback<List<Integer>>() {
            @Override
            public List<Integer> doInRedis(RedisConnection redisConnection)
                    throws DataAccessException {
                byte[] key = redisTemplate.getStringSerializer().serialize(uid);
                if (redisConnection.exists(key)) {
                    Set<byte[]> originNids = redisConnection.zRange(key, 0, -1);
                    List<Integer> nids = new ArrayList<Integer>();
                    for (byte[] o : originNids) {
                        nids.add(Integer.valueOf(redisTemplate
                                .getStringSerializer().deserialize(o)));
                    }
                   return nids;
                }
                return null;
            }
        });
    }

    @Override
    public List<String> readSetStrList(final String uid) {
        return redisTemplate.execute(new RedisCallback<List<String>>() {
            @Override
            public List<String> doInRedis(RedisConnection redisConnection)
                    throws DataAccessException {
                byte[] key = redisTemplate.getStringSerializer().serialize(uid);
                if (redisConnection.exists(key)) {
                    Set<byte[]> originNids = redisConnection.zRange(key, 0, -1);
                    List<String> nids = new ArrayList<String>();
                    for (byte[] o : originNids) {
                        nids.add(redisTemplate
                                .getStringSerializer().deserialize(o));
                    }
                    return nids;
                }
                return null;
            }
        });
    }

    @Override
    public List<Double> readSetStrListWithScores(final String uid) {
        return redisTemplate.execute(new RedisCallback<List<Double>>() {
            @Override
            public List<Double> doInRedis(RedisConnection redisConnection)
                    throws DataAccessException {
                byte[] key = redisTemplate.getStringSerializer().serialize(uid);
                if (redisConnection.exists(key)) {
                    Set<RedisZSetCommands.Tuple> originNids = redisConnection.zRangeWithScores(key, 0, -1);
                    List<Double> nids = new ArrayList<Double>();
                    for (RedisZSetCommands.Tuple o : originNids) {
                        nids.add(o.getScore());
                    }
                    return nids;
                }
                return null;
            }
        });
    }

    @Override
    public List<RedisScoreValue> readRedisScoreValue(final String uid) {
        return redisTemplate.execute(new RedisCallback< List<RedisScoreValue>>() {
            @Override
            public  List<RedisScoreValue> doInRedis(RedisConnection redisConnection)
                    throws DataAccessException {
                byte[] key = redisTemplate.getStringSerializer().serialize(uid);
                if (redisConnection.exists(key)) {
                    Set<RedisZSetCommands.Tuple> originNids = redisConnection.zRangeWithScores(key, 0, -1);
                    List<RedisScoreValue> list = new ArrayList<RedisScoreValue>();
                    RedisScoreValue obj = new RedisScoreValue();
                    for (RedisZSetCommands.Tuple o : originNids) {
                        obj.setScore(o.getScore());
                        obj.setValue( redisTemplate.getStringSerializer().deserialize(o.getValue()));
                        list.add(obj);
                        obj = new RedisScoreValue();
                    }
                    return list;
                }
                return null;
            }
        });
    }

}
