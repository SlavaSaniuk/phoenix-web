package com.phoenix.utilities.parsers;

import lombok.Getter;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component("DatabaseProperties")
public class DatabasesProperties {

    @Getter
    private String server_address;

    @Getter
    private String user_name;

    @Getter
    private String user_password;

    @Getter
    private Integer connection_pool_size;


    private DatabasesProperties() {}

    public static class Neo4jPropertiesParser extends SpringParser<DatabasesProperties> {

        //Create database properties
        private final DatabasesProperties properties = new DatabasesProperties();

        private String credentials_name = "neo4j";
        private String credentials_password = "neo4j";

        public Neo4jPropertiesParser(Environment env) {
            super(env);
        }

        private String parseConnectionProtocol() {
            return super.getEnvironment().getProperty("com.phoenix.databases.neo4j.connection.protocol", "bolt://");
        }

        private Integer parseConnectionPoolSize() {
            return super.getEnvironment().getProperty("com.phoenix.databases.neo4j.connection.pool_size", Integer.class, 50);
        }

        private String parseServerAddress() {
            return super.getEnvironment().getProperty("com.phoenix.databases.neo4j.server.address", "localhost");
        }

        private String parseServerPort() {
            return super.getEnvironment().getProperty("com.phoenix.databases.neo4j.server.port", "7687");
        }

        private String parseUserName() {
            return super.getEnvironment().getProperty("com.phoenix.databases.neo4j.credentials.user_name", "neo4j");
        }

        private String parseUserPassword() {
            return super.getEnvironment().getProperty("com.phoenix.databases.neo4j.credentials.password", "neo4j");
        }




        @Override
        public DatabasesProperties getProperties() {

            properties.connection_pool_size = this.parseConnectionPoolSize();
            properties.server_address = this.parseConnectionProtocol() + this.parseServerAddress() +":" +this.parseServerPort();
            properties.user_name = this.parseUserName();
            properties.user_password = this.parseUserPassword();

        return properties;
        }


    }
}
