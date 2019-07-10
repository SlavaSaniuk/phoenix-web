package com.phoenix.unittests.validationtests;

import com.phoenix.validation.PasswordProperties;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

@ExtendWith(MockitoExtension.class)
class PasswordPropertiesTestCase {


    @ParameterizedTest
    @ValueSource(strings = {"", "dsfsdf", "null",})
    void passwordProperties_invalidMinLength_shouldReturnZero(String invalid) {

        Environment env = Mockito.mock(Environment.class);
        BDDMockito.given(env.getProperty("com.phoenix.security.password.min_length")).willReturn(invalid);
        PasswordProperties properties = new PasswordProperties(env);
        Assertions.assertEquals(0, properties.getPasswordMinLength());

    }
}
