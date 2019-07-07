package com.phoenix.integrationdeprecated;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
public class TestDataSourceConfig {

    @Bean("testDataSource")
    @Description("DataSource for TEST profile")
    @Profile("TEST")
    public DataSource testDataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("org.h2.Driver");
        ds.setUrl("jdbc:h2:tcp://localhost/~/Projects/H2/phoenix");
        ds.setUsername("phoenix-web");
        ds.setPassword("12345678");
        return ds;
    }


}
