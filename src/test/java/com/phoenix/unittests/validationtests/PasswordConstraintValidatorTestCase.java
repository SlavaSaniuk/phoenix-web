package com.phoenix.unittests.validationtests;

import com.phoenix.validation.PasswordConstraintValidator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PasswordConstraintValidatorTestCase {

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

    @ParameterizedTest
    @ValueSource(strings = {"1234567a", "aaaaaaaaaa", "asd@asdAssrr234", "!@#sdRaAsd$#%"})
    void isValid_notSetValidationRules_shouldUseDefault(String valid) {
        PasswordConstraintValidator test = new PasswordConstraintValidator();
        Assertions.assertTrue(test.isValid(valid, null));
    }

}
