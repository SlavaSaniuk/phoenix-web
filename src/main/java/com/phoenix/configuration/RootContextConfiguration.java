package com.phoenix.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * Main Spring web application root context configuration class.
 * Class has all non web beans definition and import all configuration classes distributed by modules(packages).
 * Root context has all application business logic, persistence configuration with beans definition,
 * all services and its configurations.
 */
@Configuration
public class RootContextConfiguration {

    //LOGGER
    private static final Logger LOGGER = LoggerFactory.getLogger(RootContextConfiguration.class);

    public RootContextConfiguration() {
        LOGGER.info("Start to initialize " +getClass().getName() +" configuration class");
    }
}
