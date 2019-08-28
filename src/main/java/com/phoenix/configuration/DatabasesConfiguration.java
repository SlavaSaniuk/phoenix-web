package com.phoenix.configuration;

import org.neo4j.ogm.session.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 *  Main configuration class for setup, configuring, managing databases.
 * Class contains bean definition for use neo4j graph database
 * {@link org.neo4j.ogm.session.SessionFactory} and {@link org.neo4j.ogm.config.Configuration}..
 */
@Configuration
public class DatabasesConfiguration {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabasesConfiguration.class);

    /**
     * Main neo4j-ogm sessions factory.
     * @param neo4j_conf - {@link org.neo4j.ogm.config.Configuration} - database, driver configuration.
     * @return - Configured {@link SessionFactory} bean.
     */
    @Bean("sessionFactory")
    @Autowired
    public SessionFactory getSessionFactory(org.neo4j.ogm.config.Configuration neo4j_conf) {
        LOGGER.info("Create " +SessionFactory.class.getSimpleName() +" neo4j factory bean.");
        return new SessionFactory(neo4j_conf, "com.phoenix.models.graphs", "com.phoenix.models.graphs.posts" );
    }

    /**
     *  Configuration bean for use neo4j-ogm library,
     * connect to database in "DEVELOPMENT", "PRODUCTION" environment.
     * @return {@link org.neo4j.ogm.config.Configuration} bean.
     */
    @Bean
    @Profile({"DEVELOPMENT", "PRODUCTION"})
    public org.neo4j.ogm.config.Configuration getDevProdNeo4jConfiguration() {

        LOGGER.info("Create " + org.neo4j.ogm.config.Configuration.class.getSimpleName() +" 'Development/Production' neo4j configuration bean.");

        //Create configuration instance
        org.neo4j.ogm.config.Configuration.Builder builder = new org.neo4j.ogm.config.Configuration.Builder();

        builder.uri("bolt://localhost"); //Use bolt driver
        builder.credentials("phoenix", "12345678"); //User credentials

        //Create DEVELOPMENT/PRODUCTION configuration
        LOGGER.info(org.neo4j.ogm.config.Configuration.class.getSimpleName() +" neo4j configuration bean was created.");
        return builder.build();
    }

    /**
     *  Configuration bean for use neo4j-ogm library,
     * connect to database in "TEST environment.
     * @return {@link org.neo4j.ogm.config.Configuration} bean.
     */
    @Bean
    @Profile("TEST")
    public org.neo4j.ogm.config.Configuration getTestNeo4jConfiguration() {

        LOGGER.info("Create " + org.neo4j.ogm.config.Configuration.class.getSimpleName() +" 'Test' neo4j configuration bean.");
        org.neo4j.ogm.config.Configuration.Builder builder = new org.neo4j.ogm.config.Configuration.Builder();

        builder.uri("file:///home/user/Databases/neo4j/phoenix-test-db/phoenix-test.db");

        LOGGER.info(org.neo4j.ogm.config.Configuration.class.getSimpleName() +" neo4j configuration bean was created.");
        return builder.build();

    }

}
