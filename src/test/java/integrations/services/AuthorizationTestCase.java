package integrations.services;

import com.phoenix.configuration.RootContextConfiguration;
import com.phoenix.services.authorization.Authorization;
import integrations.beans.TestDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@ActiveProfiles("TEST")
@SpringJUnitConfig(classes = {TestDataSource.class, RootContextConfiguration.class})
class AuthorizationTestCase {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private Authorization service;

    @Test
    void configurationTest_newService_shouldNotBeNull() {
        Assertions.assertNotNull(this.service);
    }
}
