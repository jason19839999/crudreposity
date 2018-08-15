package datacenter.crudreposity.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.Persistent;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.PersistenceContext;
import javax.sql.DataSource;


@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactory2",
        transactionManagerRef = "transactionManager2",
        basePackages = "datacenter.crudreposity.dao.mysql2")
public class MysqlDatabaseConfig2 {
    @Autowired
    private MysqlConnectionSettings2 connectionStr2;

    @Bean(destroyMethod = "close")
    public HikariDataSource dataSource1() {
        HikariConfig dataSourceConfig = new HikariConfig();
        dataSourceConfig.setDriverClassName(connectionStr2.getDriver());
        dataSourceConfig.setJdbcUrl(connectionStr2.getUrl());
        dataSourceConfig.setUsername(connectionStr2.getUsername());
        dataSourceConfig.setPassword(connectionStr2.getPassword());
        dataSourceConfig.setMaximumPoolSize(1024);
        dataSourceConfig.setMinimumIdle(4);
        return new HikariDataSource(dataSourceConfig);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory1() {
        LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
        lef.setDataSource(dataSource1());
        lef.setJpaVendorAdapter(jpaVendorAdapter1());
        lef.setPackagesToScan("datacenter.crudreposity.entity.mysql2");
        return lef;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter1() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(true);
        hibernateJpaVendorAdapter.setGenerateDdl(false);
        hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);
        hibernateJpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
        return hibernateJpaVendorAdapter;
    }

    @Bean(name = "transactionManager1")
    public PlatformTransactionManager transactionManager1() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory1().getObject());
        return transactionManager;
    }

}
