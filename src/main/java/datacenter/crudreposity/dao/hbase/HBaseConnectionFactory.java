package datacenter.crudreposity.dao.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HConnection;
import org.apache.hadoop.hbase.client.HConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class HBaseConnectionFactory {

    private static Configuration configuration = null;
    private static HConnection hConnection = null;
    private static HBaseAdmin hBaseAdmin = null;

    public static final Logger logger = LoggerFactory.getLogger(HBaseConnectionFactory.class);

    /**
     * Hbase服务器读取初始化
     * @throws java.io.IOException
     * @throws org.apache.hadoop.hbase.ZooKeeperConnectionException
     * @throws org.apache.hadoop.hbase.MasterNotRunningException
     */
    public static void init() throws MasterNotRunningException, ZooKeeperConnectionException, IOException {
//		default confDir = ServingConstants.CONF_DIR
        init("");
    }

    /**
     * HDFS服务器读取初始化
     * @throws java.io.IOException
     * @throws org.apache.hadoop.hbase.ZooKeeperConnectionException
     * @throws org.apache.hadoop.hbase.MasterNotRunningException
     */
    public static void init(String confDir) throws MasterNotRunningException, ZooKeeperConnectionException, IOException {

        System.setProperty("HADOOP_USER_NAME", "hdfs");
        //读取默认配置文件
        configuration = new Configuration();

        configuration.addResource(new Path(confDir));
        configuration = HBaseConfiguration.create(configuration);
        hConnection = HConnectionManager.createConnection(configuration);
        hBaseAdmin = new HBaseAdmin(configuration);
        logger.info("Hbase Connection has been initialized");
    }

    public static void close() throws IOException {
        if(hConnection != null){
            hConnection.close();
        }
        if(hBaseAdmin != null){
            hBaseAdmin.close();
        }
    }

    public static HConnection gethConnection() {
        return hConnection;
    }

    public static HBaseAdmin gethBaseAdmin() {
        return hBaseAdmin;
    }

    public static Configuration gethConfiguration() {
        return configuration;
    }

}

