package com.company.blogapi.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.company.blogapi.repository",
        entityManagerFactoryRef = "blog-em",
        transactionManagerRef = "blog-tm"
)
public class DataBaseConfig {

    @Value("${blog.datasource.username}")
    private String datasourceUser;
    @Value("${blog.datasource.password}")
    private String datasourcePassword;
    @Value("${blog.datasource.url}")
    private String datasourceHost;
    @Value("${blog.datasource.driver}")
    private String datasourceDriver;
    @Value("${blog.hibernate.database-platform}")
    private String hibernateDatabasePlatform;
    @Value("${blog.hibernate.show-sql}")
    private boolean hibernateShowSql;
    @Value("${blog.hibernate.format-sql}")
    private boolean hibernateFormatSql;
    @Value("${blog.hibernate.ddl-auto}")
    private String hibernateDdlAuto;

    @Bean
    public DataSource dataSourceSphere() {
        HikariConfig config = new HikariConfig();
        config.setUsername(datasourceUser);
        config.setPassword(datasourcePassword);
        config.setJdbcUrl(datasourceHost);
        config.setDriverClassName(datasourceDriver);
        return new HikariDataSource(config);
    }

    @Bean(name = "blog-em")
    public LocalContainerEntityManagerFactoryBean entityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSourceSphere());
        em.setPackagesToScan("com.company.blogapi.model");
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", hibernateDdlAuto);
        properties.put("hibernate.dialect", hibernateDatabasePlatform);
        properties.put("hibernate.show_sql", hibernateShowSql);
        properties.put("hibernate.format_sql", hibernateFormatSql);
        em.setJpaPropertyMap(properties);
        return em;
    }

    @Bean(name = "blog-tm")
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManager().getObject());
        return transactionManager;
    }

}
