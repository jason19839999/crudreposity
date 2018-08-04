package datacenter.crudreposity.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @描述
 * @创建人 shicong.zhang
 * @创建时间 $date$
 * @修改人和其它信息
 */
@Component
@ConfigurationProperties(prefix = "redis")
public class RedisConnectionSettings {
    private String ip;
    private int port;
    private int database;
    private int pool_max_active; // 最大分配的对象数
    private int pool_max_idle;   // 最大能够保持idel状态的对象数
    private long pool_max_wait_time;   // 当池内没有返回对象时，最大等待时间
    private boolean pool_test_on_borrow;  // 当调用borrow Object方法时，是否进行有效性检查

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }

    public int getPool_max_active() {
        return pool_max_active;
    }

    public void setPool_max_active(int pool_max_active) {
        this.pool_max_active = pool_max_active;
    }

    public int getPool_max_idle() {
        return pool_max_idle;
    }

    public void setPool_max_idle(int pool_max_idle) {
        this.pool_max_idle = pool_max_idle;
    }

    public long getPool_max_wait_time() {
        return pool_max_wait_time;
    }

    public void setPool_max_wait_time(long pool_max_wait_time) {
        this.pool_max_wait_time = pool_max_wait_time;
    }

    public boolean isPool_test_on_borrow() {
        return pool_test_on_borrow;
    }

    public void setPool_test_on_borrow(boolean pool_test_on_borrow) {
        this.pool_test_on_borrow = pool_test_on_borrow;
    }
}