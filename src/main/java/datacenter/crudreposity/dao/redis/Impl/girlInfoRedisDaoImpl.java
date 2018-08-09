package datacenter.crudreposity.dao.redis.Impl;

import datacenter.crudreposity.dao.redis.girlInfoRedisDao;
import datacenter.crudreposity.entity.RedisScoreValue;
import datacenter.crudreposity.util.ObjectTranscoder;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
                //设置访问哪个redis实例，默认选择配置的
                redisConnection.select(12);
              /**redisConnection.set(redisTemplate.getStringSerializer().serialize(key),
                      ObjectTranscoder.serialize("test2018-08-06 OK? 大学教授来了 @#@￥@#￥#%"),
                      Expiration.seconds(300000),
                      RedisStringCommands.SetOption.UPSERT); **/

                //redisConnection.set(redisTemplate.getStringSerializer().serialize(key), ObjectTranscoder.serialize("test2018-08-06 OK? Yes Or No @#@￥@#￥#%"));
                redisConnection.zAdd(redisTemplate.getStringSerializer().serialize(key),999,redisTemplate.getStringSerializer().serialize("长春"));

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
                //设置访问哪个redis实例，默认选择配置的
                redisConnection.select(12);
                byte[] in = redisConnection.get(redisTemplate.getStringSerializer().serialize(key));
                return ObjectTranscoder.deserialize(in).toString();
            }
        });

    }

    //ZADD 命令在key后面分数/成员（score/member）对前面支持一些参数
    /** XX: 仅仅更新存在的成员，不添加新成员。
        NX: 不更新存在的成员。只添加新成员。
        CH: 修改返回值为发生变化的成员总数，原始是返回新添加成员的总数 (CH 是 changed 的意思)。更改的元素是新添加的成员，已经存在的成员更新分数。 所以在命令中指定的成员有相同的分数将不被计算在内。注：在通常情况下，ZADD返回值只计算新添加成员的数量。
       INCR: 当ZADD指定这个选项时，成员的操作就等同ZINCRBY命令，对成员的分数进行递增操作。

    Sorted sets 101
        有序集合按照分数以递增的方式进行排序。相同的成员（member）只存在一次，有序集合不允许存在重复的成员。 分数可以通过ZADD命令进行更新或者也可以通过ZINCRBY命令递增来修改之前的值，相应的他们的排序位置也会随着分数变化而改变。

        相同分数的成员
            有序集合里面的成员是不能重复的都是唯一的，但是，不同成员间有可能有相同的分数。当多个成员有相同的分数时，他们将是有序的字典（ordered lexicographically）（仍由分数作为第一排序条件，然后，相同分数的成员按照字典规则相对排序）。
            字典顺序排序用的是二进制，它比较的是字符串的字节数组。
            如果用户将所有元素设置相同分数（例如0），有序集合里面的所有元素将按照字典顺序进行排序，范围查询元素可以使用ZRANGEBYLEX命令（注：范围查询分数可以使用ZRANGEBYSCORE命令）。
     **/
    @Override
    public List<Integer> readSetIntList(final String uid) {
        return redisTemplate.execute(new RedisCallback<List<Integer>>() {
            @Override
            public List<Integer> doInRedis(RedisConnection redisConnection)
                    throws DataAccessException {
                //设置访问哪个redis实例，默认选择配置的
                redisConnection.select(12);
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
    //sorted-set 查询
    public List<String> readSetStrList(final String uid) {
        return redisTemplate.execute(new RedisCallback<List<String>>() {
            @Override
            public List<String> doInRedis(RedisConnection redisConnection)
                    throws DataAccessException {
                //设置访问哪个redis实例，默认选择配置的
                redisConnection.select(12);
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
    //sorted-set 查询
    public List<Double> readSetStrListWithScores(final String uid) {
        return redisTemplate.execute(new RedisCallback<List<Double>>() {
            @Override
            public List<Double> doInRedis(RedisConnection redisConnection)
                    throws DataAccessException {
                //设置访问哪个redis实例，默认选择配置的
                redisConnection.select(12);
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
    //sorted-set 查询
    public List<RedisScoreValue> readRedisScoreValue(final String uid) {
        return redisTemplate.execute(new RedisCallback< List<RedisScoreValue>>() {
            @Override
            public  List<RedisScoreValue> doInRedis(RedisConnection redisConnection)
                    throws DataAccessException {
                //设置访问哪个redis实例，默认选择配置的
                redisConnection.select(12);
                byte[] key = redisTemplate.getStringSerializer().serialize(uid);
                if (redisConnection.exists(key)) {
                    //正序展示
                    //Set<RedisZSetCommands.Tuple> originNids = redisConnection.zRangeWithScores(key, 0, -1);
                    //倒序展示
                    Set<RedisZSetCommands.Tuple> originNids = redisConnection.zRevRangeByScoreWithScores(key, 0, 10000);

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

    @Override
    //哈希类型保存
    public void saveHash(String key,String subkey,String subvalue) {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection redisConnection)
                    throws DataAccessException {
                //设置访问哪个redis实例，默认选择配置的
                redisConnection.select(12);
                /**redisConnection.set(redisTemplate.getStringSerializer().serialize(key),
                 ObjectTranscoder.serialize("test2018-08-06 OK? 大学教授来了 @#@￥@#￥#%"),
                 Expiration.seconds(300000),
                 RedisStringCommands.SetOption.UPSERT); **/

                //redisConnection.set(redisTemplate.getStringSerializer().serialize(key), ObjectTranscoder.serialize("test2018-08-06 OK? Yes Or No @#@￥@#￥#%"));
                //redisConnection.zAdd(redisTemplate.getStringSerializer().serialize(key),999,redisTemplate.getStringSerializer().serialize("长春"));
                //开启事务
                redisConnection.multi();
                try {
                    redisConnection.hSet(redisTemplate.getStringSerializer().serialize(key),
                            redisTemplate.getStringSerializer().serialize(subkey),
                            redisTemplate.getStringSerializer().serialize(subvalue));
                }catch (Exception ex){
                    ex.printStackTrace();
                    //回滚事务
                    redisConnection.discard();
                }finally {
                    //提交事务
                    redisConnection.exec();
                }
                return null;
            }
        });

    }

    @Override
    //读取哈希
    public RedisScoreValue readHash (final String strKey) {
        return redisTemplate.execute(new RedisCallback<RedisScoreValue>() {
            @Override
            public  RedisScoreValue doInRedis(RedisConnection redisConnection)
                    throws DataAccessException {
                //设置访问哪个redis实例，默认选择配置的
                redisConnection.select(12);
                byte[] key = redisTemplate.getStringSerializer().serialize(strKey);
                if (redisConnection.exists(key)) {
                    RedisScoreValue obj = new RedisScoreValue();
                    obj.setValue(redisTemplate.getStringSerializer().deserialize(redisConnection.hGet(redisTemplate.getStringSerializer().serialize("set_hash"),
                            redisTemplate.getStringSerializer().serialize("name"))));
                    obj.setScore(Integer.parseInt(redisTemplate.getStringSerializer().deserialize(redisConnection.hGet(redisTemplate.getStringSerializer().serialize("set_hash"),
                            redisTemplate.getStringSerializer().serialize("age")))));

                    return obj;
                }
                return null;
            }
        });
    }

}
