package com.example.postgres;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(basePackages = {"com.example.postgres.repository.tracker"},
        entityManagerFactoryRef = "dbEntityManager",
        transactionManagerRef = "dbTransactionManager")
@EnableTransactionManagement
public class ConfigTrackerDataSource {

    @Autowired
    private Environment env;

    @Bean
    public LocalContainerEntityManagerFactoryBean dbEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dbDatasource());
        em.setPackagesToScan(new String[]{"com.example.postgres.model"});
        em.setPersistenceUnitName("dbEntityManager");
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        HashMap<String, Object> properties = new HashMap<>();

        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("hibernate.show-sql", true);


        em.setJpaPropertyMap(properties);
        return em;
    }

    @Bean
    public DataSource dbDatasource() {
        DriverManagerDataSource dataSource
                = new DriverManagerDataSource();
        dataSource.setDriverClassName(
                env.getProperty("app.datasource.tracker.driver-class-name"));
        dataSource.setUrl(env.getProperty("app.datasource.tracker.url"));
        dataSource.setUsername(env.getProperty("app.datasource.tracker.username"));
        dataSource.setPassword(env.getProperty("app.datasource.tracker.password"));
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager dbTransactionManager() {
        JpaTransactionManager transactionManager
                = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                dbEntityManager().getObject());
        return transactionManager;
    }
}