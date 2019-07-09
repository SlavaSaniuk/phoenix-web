package com.phoenix.unittests.validationtests;

import com.phoenix.validation.PasswordConstraintValidator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class PasswordConstraintValidatorTestCase {


    @Mock
    Environment env;

    @InjectMocks
    PasswordConstraintValidator password_validator;

    @Test
    @Order(1)
    void isValid_passwordNull_shouldReturnFalse() {
        PasswordConstraintValidator test = new PasswordConstraintValidator();
        Assertions.assertFalse(test.isValid(null, null));
    }

    @Test
    @Order(2)
    void isValid_lengthLessThanMinimum_shouldReturnFalse() {

        PasswordConstraintValidator test = new PasswordConstraintValidator();
        test.setMinPasswordLength(10);
        String invalid = "a12345678";

        Assertions.assertFalse(test.isValid(invalid, null));

    }

    @Test
    @Order(3)
    void isValid_passwordWithUppercaseSymbol_shouldReturnTrue() {
        PasswordConstraintValidator test = new PasswordConstraintValidator();
        test.setUppercaseLetter(true);
        String valid = "aaaaaAaaaaa";
        Assertions.assertTrue(test.isValid(valid, null));

    }


    @Test
    @Order(4)
    void isValid_passwordWithoutUpperCaseCharacters_shouldReturnFalse() {
        PasswordConstraintValidator test = new PasswordConstraintValidator();
        test.setUppercaseLetter(true);
        String invalid = "aaaaaaaaaaa";

        Assertions.assertFalse(test.isValid(invalid, null));
    }

    @Test
    @Order(5)
    void isValid_passwordWithLowercaseSymbol_shouldReturnTrue() {
        PasswordConstraintValidator test = new PasswordConstraintValidator();
        test.setLowercaseLetter(true);
        String valid = "AAAAaAAAAA";
        Assertions.assertTrue(test.isValid(valid, null));

    }


    @Test
    @Order(6)
    void isValid_passwordWithoutLowercaseCharacters_shouldReturnFalse() {
        PasswordConstraintValidator test = new PasswordConstraintValidator();
        test.setLowercaseLetter(true);
        String invalid = "AADF123#$%";

        Assertions.assertFalse(test.isValid(invalid, null));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"aADFaaaaa9a", "3aaaFDSaaaaa", "aaFDFS)(FS?/aaaaa7"})
    @Order(7)
    void isValid_passwordWithNumber_shouldReturnTrue(String valid) {
        PasswordConstraintValidator test = new PasswordConstraintValidator();
        test.setNumber(true);
        Assertions.assertTrue(test.isValid(valid, null));

    }


    @Test
    @Order(8)
    void isValid_passwordWithoutNumber_shouldReturnFalse() {
        PasswordConstraintValidator test = new PasswordConstraintValidator();
        test.setNumber(true);
        String invalid = "AADFasdsd#$%";

        Assertions.assertFalse(test.isValid(invalid, null));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"aADFaaa!aa9a", "3a\\aaFDSaaaaa", "/aaFDFS)(FS?/aaaaa7", "!asdasd485nsnf", "asdoir395#"})
    @Order(9)
    void isValid_passwordWithSpecial_shouldReturnTrue(String valid) {
        PasswordConstraintValidator test = new PasswordConstraintValidator();
        test.setSpecialCharacter(true);
        Assertions.assertTrue(test.isValid(valid, null));

    }


    @Test
    @Order(10)
    void isValid_passwordWithoutSpecial_shouldReturnFalse() {
        PasswordConstraintValidator test = new PasswordConstraintValidator();
        test.setSpecialCharacter(true);
        String invalid = "AADFasdsdasd098";

        Assertions.assertFalse(test.isValid(invalid, null));
    }

    @Order(11)
    @ParameterizedTest
    @ValueSource(strings = {"1234567a", "aaaaaaaaaa", "asd@asdAssrr234", "!@#sdRaAsd$#%"})
    void isValid_notSetValidationRules_shouldUseDefault(String valid) {
        PasswordConstraintValidator test = new PasswordConstraintValidator();
        Assertions.assertTrue(test.isValid(valid, null));
    }

    @Test
    void initializeProperties_notSetSecurityProperty_shouldUseDefault() {

        Mockito.when(env.getProperty("com.phoenix.security.password.min_length","8")).thenReturn("");
        Mockito.when(env.getProperty("com.phoenix.security.password.contain_uppercase","false")).thenReturn("");
        Mockito.when(env.getProperty("com.phoenix.security.password.contain_lowercase","true")).thenReturn("");
        Mockito.when(env.getProperty("com.phoenix.security.password.contain_numbers","false")).thenReturn("");
        Mockito.when(env.getProperty("com.phoenix.security.password.contain_special_characters","false")).thenReturn("");

        this.password_validator.initializeProperties();

        Assertions.assertEquals(8, this.password_validator.getMinPasswordLength());
        Assertions.assertFalse(this.password_validator.isUppercaseLetter());
        Assertions.assertTrue(this.password_validator.isLowercaseLetter());
        Assertions.assertFalse(this.password_validator.isNumber());
        Assertions.assertFalse(this.password_validator.isSpecialCharacter());
    }

    @Test
    void initializeProperties_invalidProperties_shouldUseDefault() {
        BDDMockito.given(this.env.getProperty("com.phoenix.security.password.contain_uppercase","false")).willReturn("asdasd");
        this.password_validator.initializeProperties();
        Assertions.assertFalse(this.password_validator.isUppercaseLetter());
    }

    @Test
    void initializeProperties_validProperties_shouldUseProperty() {
        Mockito.when(env.getProperty("com.phoenix.security.password.contain_uppercase","false")).thenReturn("true");
        this.password_validator.initializeProperties();
        Assertions.assertTrue(this.password_validator.isUppercaseLetter());
    }
}
