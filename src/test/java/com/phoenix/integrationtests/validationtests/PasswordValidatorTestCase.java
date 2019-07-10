package com.phoenix.integrationtests.validationtests;


import com.phoenix.integrationtests.configurationfortests.ValidationTestConfiguration;
import com.phoenix.models.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

@SpringJUnitConfig(classes = ValidationTestConfiguration.class)
class PasswordValidatorTestCase {

    private Validator validator;

    @Test
    void validate_invalidPassword_shouldHasError() {
        Account test = new Account();
        test.setAccountEmail("email@email.com");

        test.setAccountPassword("AAAAAAAAA");
        BindingResult result = new BeanPropertyBindingResult(test, "test");
        validator.validate(test, result);

        Assertions.assertTrue(result.hasErrors());
    }

    @Test
    void validate_passwordLengthLessThanMinPasswordLength_shouldHasError() {
        Account test = new Account();
        test.setAccountEmail("email@email.com");

        test.setAccountPassword("a3D!");
        BindingResult result = new BeanPropertyBindingResult(test, "test");
        validator.validate(test, result);

        Assertions.assertTrue(result.hasErrors());
    }

    @Test
    void validate_passwordNotContainSpecialCharacter_shouldHasError() {
        Account test = new Account();
        test.setAccountEmail("email@email.com");

        test.setAccountPassword("aaaDDD123");
        BindingResult result = new BeanPropertyBindingResult(test, "test");
        validator.validate(test, result);

        Assertions.assertTrue(result.hasErrors());
    }


    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    void setValidator(Validator validator) {
        this.validator = validator;
    }
}
