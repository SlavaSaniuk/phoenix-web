package com.phoenix.configuration;

import com.phoenix.exceptions.FileCorruptException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
@PropertySource(value = "classpath:/configuration-files/persistence.properties")
public class PersistenceConfiguration {

    //LOGGER
    private static final Logger LOGGER = LoggerFactory.getLogger(PersistenceConfiguration.class);
    //Spring beans
    private Environment env; //autowired

    /**
     * Constructor used by Spring to automatically set {@link Environment} bean.
     * {@link Environment} reads configuration properties from "persistence.properties" under
     * "resources/configuration-files/" directory. Env used as beans that contains all configuration properties for application persistence.
     * @param a_env - Autowired Spring {@link Environment}.
     */
    @Autowired
    public PersistenceConfiguration(Environment a_env) {
        LOGGER.info("Start to initialize " +getClass().getName() +" configuration class");
        LOGGER.info("Start to loading persistence configuration properties from \"persistence.properties\" file.");
        this.env = a_env; //Spring autowiring
    }

    /**
     * Development {@link DataSource} bean has an implementation of {@link DriverManagerDataSource} class.
     * Bean get configuration properties from {@link PersistenceConfiguration#env} bean
     * which initialized in this class constructor {@link PersistenceConfiguration#PersistenceConfiguration(Environment)}.
     * Development DataSource used only for development parameters because is so slow for production use.
     * @return - Configured {@link DataSource} bean.
     */
    @Bean("developmentDataSource")
    @Description("DataSource for DEVELOPMENT profile")
    public DataSource developmentDataSource() {

        LOGGER.debug("Create DEVELOPMENT DataSource bean");
        DriverManagerDataSource ds = new DriverManagerDataSource();

        //Set parameters to DataSource
        //Set database url
        LOGGER.debug("Set configuration parameters to DataSource");
        try {
            String database_url = env.getProperty("com.phoenix.persistence.datasource.development.database_url");
            if (database_url == null ) throw new FileCorruptException("persistence.properties");
            if (database_url.isEmpty()) throw  new IllegalArgumentException("Properties \"com.phoenix.persistence.datasource.development.database_url\" is not set.");
            ds.setUrl(database_url);
            LOGGER.debug("DataSource URL parameter is installed to: " +database_url +" value");
        }catch (FileCorruptException exc) {
            LOGGER.error(exc.toString());
            LOGGER.error("Please, replace corrupted file with new persistence.properties file.");
            System.exit(1);
        }catch (IllegalArgumentException exc) {
            LOGGER.error(exc.toString());
            LOGGER.error("Please. set \"com.phoenix.persistence.datasource.development.database_url\" in persistence.properties file.");
            System.exit(1);
        }

        //Set driver class name
        try {
            String driver_class_name = env.getProperty("com.phoenix.persistence.datasource.development.driver_class");
            if (driver_class_name == null ) throw new FileCorruptException("persistence.properties");
            if (driver_class_name.isEmpty()) {
                LOGGER.warn("Property \"com.phoenix.persistence.datasource.development.driver_class\" in \"persistence.properties\" file is not set. "
                        +"Application will use DEFAULT MySQL \"com.mysql.cj.jdbc.Driver\" driver.");
                driver_class_name = "com.mysql.cj.jdbc.Driver";
            }
            ds.setDriverClassName(driver_class_name);
            LOGGER.debug("DataSource driverClassName parameter is installed to: " +driver_class_name +" value");
        }catch (FileCorruptException exc) {
            LOGGER.error(exc.toString());
            LOGGER.error("Please, replace corrupted file with new persistence.properties file.");
            System.exit(1);
        }

        //Set user name
        try {
            String user_name = env.getProperty("com.phoenix.persistence.datasource.development.user_name");
            if (user_name == null ) throw new FileCorruptException("persistence.properties");
            if (user_name.isEmpty()) {
                LOGGER.warn("Property \"com.phoenix.persistence.datasource.development.user_name\" in \"persistence.properties\" file is not set. "
                        +"Application will use DEFAULT \"root\" account.");
                user_name = "root";
            }
            ds.setUsername(user_name);
            LOGGER.debug("DataSource Username parameter is installed to: " +user_name +" value");
        }catch (FileCorruptException exc) {
            LOGGER.error(exc.toString());
            LOGGER.error("Please, replace corrupted file with new persistence.properties file.");
            System.exit(1);
        }

        //Set password
        try {
            String user_password = env.getProperty("com.phoenix.persistence.datasource.development.user_password");
            if (user_password == null ) throw new FileCorruptException("persistence.properties");
            if (user_password.isEmpty()) {
                LOGGER.warn("Property \"com.phoenix.persistence.datasource.development.user_password\" in \"persistence.properties\" file is not set. "
                        +"Application will use DEFAULT empty password account.");
            }
            ds.setPassword(user_password);
            LOGGER.debug("DataSource driverClassName parameter is installed in empty value");
        }catch (FileCorruptException exc) {
            LOGGER.error(exc.toString());
            LOGGER.error("Please, replace corrupted file with new persistence.properties file.");
            System.exit(1);
        }

        LOGGER.debug("Configuring development DataSource is created");
         return ds;
    }





}
