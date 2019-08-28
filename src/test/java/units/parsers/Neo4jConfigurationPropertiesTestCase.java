package units.parsers;

import com.phoenix.configuration.properties.Neo4jConfigurationProperties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

class Neo4jConfigurationPropertiesTestCase {

    private MockEnvironment environment;

    @BeforeEach
    void setUpBeforeEach() {
        this.environment = new MockEnvironment();
    }

    @AfterEach
    void tearDownAfterEach() {
        this.environment = null;
    }

    @Test
    void getProperties_propertyIsNull_shouldUseDefault() {

        Neo4jConfigurationProperties properties = new Neo4jConfigurationProperties.Neo4jEnvironmentParser().environment(this.environment).getProperties();
        Assertions.assertEquals("bolt://",properties.getConnection_protocol());
    }

    @Test
    void getProperties_propertyIsEmpty_shouldUseDefault() {

        this.environment.setProperty("com.phoenix.databases.neo4j.connection.protocol", "");

        Neo4jConfigurationProperties properties = new Neo4jConfigurationProperties.Neo4jEnvironmentParser().environment(this.environment).getProperties();
        Assertions.assertEquals("bolt://",properties.getConnection_protocol());
    }

    @Test
    void getProperties_propertyHasValue_shouldUseThisValue() {

        this.environment.setProperty("com.phoenix.databases.neo4j.connection.protocol", "hello");

        Neo4jConfigurationProperties properties = new Neo4jConfigurationProperties.Neo4jEnvironmentParser().environment(this.environment).getProperties();
        Assertions.assertEquals("hello://",properties.getConnection_protocol());
    }


}
