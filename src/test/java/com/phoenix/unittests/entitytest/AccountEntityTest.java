package com.phoenix.unittests.entitytest;

import com.phoenix.models.Account;
import com.phoenix.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.*;
import java.util.Set;

class AccountEntityTest {

    private static Validator hibernate_validator;

    @BeforeAll
    static void setUpBeforeAll() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        hibernate_validator = factory.getValidator();
    }

    @Test
    void newAccount_defaultConstructor_shouldReturnAccountDefaultId() {
        Assertions.assertEquals(0, new Account().getAccountId());
    }

    @Test
    void setAccountEmail_setString_shouldReturnExpectedString() {
        String expected = "test";
        Account account = new Account();
        account.setAccountEmail(expected);
        Assertions.assertEquals(expected, account.getAccountEmail());
    }

    @Test
    void setAccountEmail_setEmptyValue_shouldBeConstraintViolation() {
        Account test = new Account();
        test.setAccountEmail("");
        test.setAccountPassword("12345678");

        Set<ConstraintViolation<Account>> violation = hibernate_validator.validate(test);

        Assertions.assertEquals(1, violation.size());
    }

    @Test
    void setAccountEmail_setNullValue_shouldBeConstraintViolations() {
        Account test = new Account();
        test.setAccountEmail(null);
        test.setAccountPassword("12345678");

        Set<ConstraintViolation<Account>> violation = hibernate_validator.validate(test);

        Assertions.assertEquals(2, violation.size());
    }

    @Test
    void setAccountPassword_setString_shouldReturnExpectedString() {
        String expected = "test";
        Account account = new Account();
        account.setAccountPassword(expected);
        Assertions.assertEquals(expected, account.getAccountPassword());
    }

    @Test
    void setAccountPassword_setEmptyString_shouldBeConstraintViolation() {
        Account test = new Account();
        test.setAccountEmail("test");
        test.setAccountPassword("");

        Set<ConstraintViolation<Account>> violations = hibernate_validator.validate(test);

        Assertions.assertEquals(1, violations.size());
    }

    @Test
    void setAccountPassword_setNullValue_shouldBeConstraintViolations() {
        Account test = new Account();
        test.setAccountEmail("test");
        test.setAccountPassword(null);

        Set<ConstraintViolation<Account>> violations = hibernate_validator.validate(test);

        Assertions.assertEquals(2, violations.size());
    }

    @Test
    void newAccount_defaultConstructor_shouldBeConstraintsViolations() {
        Account account = new Account();

        Set<ConstraintViolation<Account>> violations = hibernate_validator.validate(account);
        Assertions.assertEquals(4, violations.size());
    }

    @Test
    void setAccountOwner_newUser_shouldBeSetted() {
        User test = new User();
        Account account = new Account();
        account.setAccountOwner(test);
        Assertions.assertEquals(test, account.getAccountOwner());
    }

}
