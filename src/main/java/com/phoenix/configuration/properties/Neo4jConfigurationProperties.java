package com.phoenix.configuration.properties;

import com.phoenix.utilities.parsers.SpringEnvironmentPropertiesParser;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

public class Neo4jConfigurationProperties {

    //LOGGER
    private static final Logger LOGGER = LoggerFactory.getLogger(Neo4jConfigurationProperties.class);

    //Connection protocols
    private enum PROTOCOLS {BOLT, HTTP}

    //Available properties
    @Getter
    private String connection_protocol;



    public static class Neo4jEnvironmentParser extends SpringEnvironmentPropertiesParser<Neo4jConfigurationProperties> {

        private final Neo4jConfigurationProperties properties = new Neo4jConfigurationProperties(); //Resulting properties

        private void parseConnectionProtocol() {

            //Get "com.phoenix.databases.neo4j.connection.protocol" property
            String s = super.getEnvironment().getProperty("com.phoenix.databases.neo4j.connection.protocol", PROTOCOLS.BOLT.toString().toLowerCase());

            //Check if property is set
            if (s.isEmpty()) {
                //Use default
                LOGGER.warn("Connection protocol is not set. Use default \"bolt\" protocol.");
                properties.connection_protocol = PROTOCOLS.BOLT.toString().toLowerCase() +"://";
            }else {
                //Use this property
                LOGGER.debug("Connection protocol is set to \"" +s +"\" protocol.");
                properties.connection_protocol = s.toLowerCase() +"://";
            }
        }

        public Neo4jEnvironmentParser environment(Environment environment) {
            super.setEnvironment(environment);
            return this;
        }

        @Override
        protected Neo4jConfigurationProperties parseEnvironment() {

            //Parse properties
            parseConnectionProtocol();

            return properties;
        }
    }


}
