package datacenter.crudreposity.config;
/**
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import redis.clients.jedis.JedisPoolConfig;

import java.io.Serializable;
**/
/**
 * @描述
 * @创建人 shicong.zhang
 * @创建时间 $date$
 * @修改人和其它信息
 */
//@Configuration
//@EnableRedisRepositories("datacenter.crudreposity.dao.redis")
public class RedisConfig {
    /**
    @Autowired
    private RedisConnectionSettings redisConnectionSettings;

    //@Bean
    public JedisConnectionFactory jedisConnectionFactory(){
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setDatabase(redisConnectionSettings.getDatabase());
        jedisConnectionFactory.setHostName(redisConnectionSettings.getIp());
        jedisConnectionFactory.setPort(redisConnectionSettings.getPort());
        jedisConnectionFactory.setUsePool(true);
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig());
        return jedisConnectionFactory;
    }

    //@Bean
    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(redisConnectionSettings.getPool_max_active());
        jedisPoolConfig.setMaxIdle(redisConnectionSettings.getPool_max_idle());
        jedisPoolConfig.setMaxWaitMillis(redisConnectionSettings.getPool_max_wait_time());
        jedisPoolConfig.setTestOnBorrow(redisConnectionSettings.isPool_test_on_borrow());
        return jedisPoolConfig;
    }

    //@Bean
    public RedisTemplate<Serializable,Serializable> redisTemplate(){
        RedisTemplate<Serializable,Serializable> redisTemplate = new RedisTemplate<Serializable, Serializable>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisTemplate;
    }
    **/
}
