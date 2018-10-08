package datacenter.crudreposity.dao.redis;

import datacenter.crudreposity.entity.Girlnfo;
import datacenter.crudreposity.entity.RedisScoreValue;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @描述
 * @创建人 shicong.zhang
 * @创建时间 $date$
 * @修改人和其它信息
 */
@Service
public interface girlInfoRedisDao {
    void save(String key);
    String read(String key);
    List<Integer> readSetIntList(final String uid);
    List<String> readSetStrList(final String uid);
    List<Double> readSetStrListWithScores(final String uid);
    List<RedisScoreValue> readRedisScoreValue(final String uid);
    void saveHash(String key,String subkey,String subvalue);
    RedisScoreValue readHash (final String uid);
    void saveStock(String key,int count);
    Integer readStockCount(final String key);
    Integer readStockCountByStringRedisTemplate(final String key);
}
