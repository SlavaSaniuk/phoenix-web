package com.phoenix.integrationtests.validationtests;

import com.phoenix.integrationtests.configurationfortests.ValidationTestConfiguration;
import com.phoenix.validation.PasswordProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@SpringJUnitConfig(classes = ValidationTestConfiguration.class)
class PasswordPropertiesTestCase {

    @Autowired
    private PasswordProperties properties;

    @Test
    @Disabled
    void passwordProperties_propertiesIsNotSet_shouldReturnAllNullable1Properties() {
        Assertions.assertEquals(0, this.properties.getPasswordMinLength());
        Assertions.assertNull(this.properties.getPasswordUppercase());
        Assertions.assertNull(this.properties.getPasswordLowercase());
        Assertions.assertNull(this.properties.getPasswordNumbers());
        Assertions.assertNull(this.properties.getPasswordSpecial());
    }

    @Test
    void passwordProperties_propertiesIsSet_shouldUseThisProperties() {
        Assertions.assertEquals(9, this.properties.getPasswordMinLength());
        Assertions.assertEquals("true", this.properties.getPasswordNumbers());
    }
}
