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
@ConfigurationProperties(value = "mysql")
public class MysqlConnectionSettings {

    private String driver;
    private MysqlConnectionStr master;
    private MysqlConnectionStr replica1;

    public MysqlConnectionSettings() {}

    public String getDriver() {
        return driver;
    }
    public void setDriver(String driver)
    {
        this.driver = driver;
    }

    public MysqlConnectionStr getMaster() {
        return master;
    }

    public void setMaster(MysqlConnectionStr master) {
        this.master = master;
    }

    public MysqlConnectionStr getReplica1() {
        return replica1;
    }

    public void setReplica1(MysqlConnectionStr replica1) {
        this.replica1 = replica1;
    }
}
