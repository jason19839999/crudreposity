package datacenter.crudreposity.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * @描述
 * @创建人 shicong.zhang
 * @创建时间 $date$
 * @修改人和其它信息
 */
@Configuration
@EnableJpaRepositories(basePackages = "datacenter.crudreposity.dao.mysql")
public class MysqlDatabaseConfig {
    @Autowired
    private MysqlConnectionSettings connectionSettings;

    @Bean(destroyMethod = "close")
    public HikariDataSource masterDataSource() {
        HikariConfig dataSourceConfig = new HikariConfig();
        dataSourceConfig.setDriverClassName(connectionSettings.getDriver());
        dataSourceConfig.setJdbcUrl(connectionSettings.getMaster().getUrl());
        dataSourceConfig.setUsername(connectionSettings.getMaster().getUsername());
        dataSourceConfig.setPassword(connectionSettings.getMaster().getPassword());
        dataSourceConfig.setMaximumPoolSize(1024);
        dataSourceConfig.setMinimumIdle(4);
        return new HikariDataSource(dataSourceConfig);
    }

    @Bean(destroyMethod = "close")
    @Primary
    public HikariDataSource replica1DataSource() {
        HikariConfig dataSourceConfig = new HikariConfig();
        dataSourceConfig.setDriverClassName(connectionSettings.getDriver());
        dataSourceConfig.setJdbcUrl(connectionSettings.getReplica1().getUrl());
        dataSourceConfig.setUsername(connectionSettings.getReplica1().getUsername());
        dataSourceConfig.setPassword(connectionSettings.getReplica1().getPassword());
        dataSourceConfig.setMaximumPoolSize(1024);
        dataSourceConfig.setMinimumIdle(4);
        return new HikariDataSource(dataSourceConfig);
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
        lef.setDataSource(roundRobinDataSourceProxy());
        lef.setJpaVendorAdapter(jpaVendorAdapter());
        lef.setJpaProperties(jpaProperties());
        lef.setPackagesToScan("datacenter.crudreposity.entity");
        return lef;
    }

    @Bean
    public AbstractRoutingDataSource roundRobinDataSourceProxy(){
        RoutingDataSource proxy = new RoutingDataSource();
        Map<Object,Object> targetDataResources = new HashMap<>();
        targetDataResources.put(DbContextHolder.DbType.MASTER,masterDataSource());
        targetDataResources.put(DbContextHolder.DbType.REPLICA1,replica1DataSource());
        proxy.setDefaultTargetDataSource(replica1DataSource());//默认源
        proxy.setTargetDataSources(targetDataResources);
        proxy.afterPropertiesSet();
        return proxy;
    }

    @Bean
    public Properties jpaProperties(){
        Properties properties = new Properties();
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.id.new_generator_mappings","false");
        return properties;
    }
    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(false);
        hibernateJpaVendorAdapter.setGenerateDdl(false);
        hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);
        hibernateJpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
        return hibernateJpaVendorAdapter;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }
}