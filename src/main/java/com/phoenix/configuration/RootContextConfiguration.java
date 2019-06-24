package com.phoenix.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Main Spring web application root context configuration class.
 * Class has all non web beans definition and import all configuration classes distributed by modules(packages).
 * Root context has all application business logic, persistence configuration with beans definition,
 * all services and its configurations.
 */
@Configuration
@Import(PersistenceConfiguration.class)
public class RootContextConfiguration {

    //LOGGER
    private static final Logger LOGGER = LoggerFactory.getLogger(RootContextConfiguration.class);

    public RootContextConfiguration() {
        LOGGER.info("Start to initialize " +getClass().getName() +" configuration class");
    }

    @Bean("jndiContext")
    @Profile("PRODUCTION")
    public Context jndiContext() {
        Context env;
        try {
            Context init_ctx = new InitialContext();
            env = (Context) init_ctx.lookup("java:comp/env");
        }catch (NamingException exc) {
            LOGGER.error(exc.getMessage());
            throw new BeanCreationException("Can't to initialize a JNDI context.", exc);
        }
        return env;
    }
}
