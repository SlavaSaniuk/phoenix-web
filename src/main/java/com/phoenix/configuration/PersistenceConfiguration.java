package com.phoenix.configuration;

import com.phoenix.exceptions.FileCorruptException;
import javax.naming.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.naming.NamingException;
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
    @Profile("DEVELOPMENT")
    public DataSource developmentDataSource() throws BeanCreationException {

        LOGGER.debug("Create DEVELOPMENT DataSource bean");
        DriverManagerDataSource ds = new DriverManagerDataSource();

        //Set parameters to DataSource
        //Set database url
        LOGGER.debug("Set configuration parameters to DataSource");
        try {
            String database_url = env.getProperty("com.phoenix.persistence.datasource.development.database_url");
            if (database_url == null ) throw new FileCorruptException("persistence.properties");
            if (database_url.isEmpty()) throw  new IllegalArgumentException("Property \"com.phoenix.persistence.datasource.development.database_url\" is not set.");
            ds.setUrl(database_url);
            LOGGER.debug("DataSource URL parameter is installed to: " +database_url +" value");
        }catch (FileCorruptException exc) {
            LOGGER.error(exc.toString());
            LOGGER.error("Please, replace corrupted file with new persistence.properties file.");
            throw new BeanCreationException("Property \"com.phoenix.persistence.datasource.development.database_url\" is not set.");
        }catch (IllegalArgumentException exc) {
            LOGGER.error(exc.toString());
            LOGGER.error("Please. set \"com.phoenix.persistence.datasource.development.database_url\" in persistence.properties file.");
            throw new BeanCreationException("Property \"com.phoenix.persistence.datasource.development.database_url\" is not set.");
        }

        //Set driver class name
        String driver_class_name = env.getProperty("com.phoenix.persistence.datasource.development.driver_class");
        if (driver_class_name == null || driver_class_name.isEmpty()) {
            LOGGER.warn("Property \"com.phoenix.persistence.datasource.development.driver_class\" in \"persistence.properties\" file is not set. "
                    +"Application will use DEFAULT MySQL \"com.mysql.cj.jdbc.Driver\" driver.");
            driver_class_name = "com.mysql.cj.jdbc.Driver";
        }
        ds.setDriverClassName(driver_class_name);
        LOGGER.debug("DataSource driverClassName parameter is installed to: " +driver_class_name +" value");



        //Set user name
        String user_name = env.getProperty("com.phoenix.persistence.datasource.development.user_name");
        if (user_name == null || user_name.isEmpty()) {
            LOGGER.warn("Property \"com.phoenix.persistence.datasource.development.user_name\" in \"persistence.properties\" file is not set. "
                    +"Application will use DEFAULT \"root\" account.");
            user_name = "root";
        }
        ds.setUsername(user_name);
        LOGGER.debug("DataSource Username parameter is installed to: " +user_name +" value");


        //Set password
        String user_password = env.getProperty("com.phoenix.persistence.datasource.development.user_password");
        if (user_password == null || user_password.isEmpty()) {
            LOGGER.warn("Property \"com.phoenix.persistence.datasource.development.user_password\" in \"persistence.properties\" file is not set. "
                    +"Application will use DEFAULT empty password account.");
            user_password = "";
        }
        ds.setPassword(user_password);
        LOGGER.debug("DataSource user_password parameter is installed in empty value");

        LOGGER.debug("Configuring development DataSource is created");
        return ds;
    }

    @Bean("productionDataSource")
    @Description("DataSource for PRODUCTION profile")
    @Profile("PRODUCTION")
    @Autowired
    public DataSource productionDataSource(Context jndi_context) throws BeanCreationException {
        LOGGER.info("Start to create PRODUCTION DataSource bean");
        DataSource ds;
        String jndi_name = this.env.getProperty("com.phoenix.persistence.datasource.production.jndi-name");
        try {
            if (jndi_name == null) throw new FileCorruptException("persistence.properties");
            if (jndi_name.isEmpty()) {
                jndi_name = "jdbc/phoenix";
                LOGGER.warn("Property \"com.phoenix.persistence.datasource.production.jndi-name\" is not set. Application will use DEFAULT " +jndi_name +" value." );
            }
            ds = (DataSource) jndi_context.lookup(jndi_name);
        }catch (FileCorruptException exc) {
            LOGGER.error(exc.toString());
            throw new BeanCreationException("Property \"com.phoenix.persistence.datasource.production.jndi-name\" is not set.", exc);
        }catch (NamingException exc) {
            LOGGER.error(exc.toString(), exc);
            throw new BeanCreationException("DataSource with JNDI name: " +jndi_name +" was not found to JNDI context.", exc);
        }
        return ds;
    }





}
