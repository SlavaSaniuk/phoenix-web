package com.phoenix.unittests.validationtests;

import com.phoenix.validation.PasswordConstraintValidator;
import com.phoenix.validation.PasswordProperties;
import com.phoenix.validation.annotations.Password;
import java.lang.annotation.Annotation;
import javax.validation.Payload;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@SuppressWarnings("unchecked")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PasswordConstraintValidatorTestCase {


    @Mock
    private PasswordProperties properties;

    @InjectMocks
    private PasswordConstraintValidator password_validator;

    private Password valid_impl = new Password(){

        @Override
        public Class<? extends Annotation> annotationType() {
            return null;
        }

        @Override
        public String message() {
            return null;
        }

        @Override
        public Class<?>[] groups() {
            return new Class[0];
        }

        @Override
        public Class<? extends Payload>[] payload() {
            return new Class[0];
        }

        @Override
        public int min_length() {
            return 8;
        }

        @Override
        public boolean uppercase() {
            return false;
        }

        @Override
        public boolean lowercase() {
            return true;
        }

        @Override
        public boolean numbers() {
            return false;
        }

        @Override
        public boolean special() {
            return false;
        }
    };

    private Password invalid_impl = new Password() {

        @Override
        public Class<? extends Annotation> annotationType() {
            return null;
        }

        @Override
        public String message() {
            return null;
        }

        @Override
        public Class<?>[] groups() {
            return new Class[0];
        }

        @Override
        public Class<? extends Payload>[] payload() {
            return new Class[0];
        }

        @Override
        public int min_length() {
            return 0;
        }

        @Override
        public boolean uppercase() {
            return false;
        }

        @Override
        public boolean lowercase() {
            return false;
        }

        @Override
        public boolean numbers() {
            return false;
        }

        @Override
        public boolean special() {
            return false;
        }
    };

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

    @ParameterizedTest()
    @ValueSource(ints = {0, -3})
    void initialize_minLengthIsInvalid_shouldUseDefault(int invalid) {
        BDDMockito.given(this.properties.getPasswordMinLength()).willReturn(invalid);
        this.password_validator.initialize(this.valid_impl);
        Assertions.assertEquals(8, this.password_validator.getMinPasswordLength());
    }

    @ParameterizedTest()
    @ValueSource(ints = {0, -3})
    void initialize_minLengthAndAnnotationAreInvalid_shouldUseDefault(int invalid) {
        BDDMockito.given(this.properties.getPasswordMinLength()).willReturn(invalid);
        this.password_validator.initialize(this.invalid_impl);
        Assertions.assertEquals(8, this.password_validator.getMinPasswordLength());
    }

    @Test
    void initialize_minLengthValid_shouldUseThis() {
        BDDMockito.given(this.properties.getPasswordMinLength()).willReturn(23);
        this.password_validator.initialize(this.valid_impl);
        Assertions.assertEquals(23, this.password_validator.getMinPasswordLength());
    }

    @ParameterizedTest
    @ValueSource(strings = {"sadasd", "False", ""})
    void initialize_booleanPropertiesNotValid_shouldUseDefault(String invalid) {
        BDDMockito.given(this.properties.getPasswordUppercase()).willReturn(invalid);
        this.password_validator.initialize(this.valid_impl);
        Assertions.assertFalse(this.password_validator.isUppercaseLetter());
    }


}
