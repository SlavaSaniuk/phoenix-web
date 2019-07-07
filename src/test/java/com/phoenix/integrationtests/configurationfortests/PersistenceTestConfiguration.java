package com.phoenix.integrationtests.configurationfortests;

import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Profile("TEST")
@EnableTransactionManagement
public class PersistenceTestConfiguration {

    @Bean
    @Description("DataSource for test purposes")
    public DataSource testDataSource() {

        BasicDataSource ds = new BasicDataSource();

        ds.setDriverClassName("org.h2.Driver");
        ds.setUrl("jdbc:h2:tcp://localhost/~/Projects/H2/phoenix");
        ds.setUsername("phoenix-web");
        ds.setPassword("12345678");

        return ds;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(this.testDataSource());
        emf.setPackagesToScan("com.phoenix.models");
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emf.setJpaProperties(this.jpaTestProperties());

        return emf;
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager() {
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(this.entityManagerFactory().getObject());
        return manager;
    }

    private Properties jpaTestProperties() {
        Properties props = new Properties();
        props.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        props.setProperty("hibernate.hbm2ddl.auto", "validate");
        return props;
    }
}

