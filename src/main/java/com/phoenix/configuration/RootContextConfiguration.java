package com.phoenix.configuration;

import com.phoenix.repositories.RepositoriesConfiguration;
import com.phoenix.services.ServicesConfiguration;
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
@Import({PersistenceConfiguration.class,
                RepositoriesConfiguration.class,
                ServicesConfiguration.class})
public class RootContextConfiguration {

    //LOGGER
    private static final Logger LOGGER = LoggerFactory.getLogger(RootContextConfiguration.class);

    /**
     * Default constructor. Inform about loading of this configuration file.
     */
    public RootContextConfiguration() {
        LOGGER.info("Start to initialize " +getClass().getName() +" configuration class");
    }

    /**
     * JNDI native context bean. Used for getting object from JNDI.
     * For example for getting tomcat controlled production datasource.
     * @return - Configured {@link Context} with link on "java:comp/env" subcontext.
     * @throws BeanCreationException - If JNDI context in can't to found JNDI required object.
     */
    @Bean("jndiContext")
    @Profile("PRODUCTION")
    public Context jndiContext() throws BeanCreationException {
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
