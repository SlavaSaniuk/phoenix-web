package com.phoenix.integrationtests.validationtests;

import com.phoenix.integrationtests.configurationfortests.ValidationTestConfiguration;
import com.phoenix.models.Account;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;


@SpringJUnitConfig(classes = ValidationTestConfiguration.class)
class PasswordValidatorTestCase {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordValidatorTestCase.class);

    //Spring beans
    private Validator validator;

    @Test
    void validate_passwordIsNull_shouldBeNonValid() {

        Account account = new Account();
        account.setAccountEmail("test@test.com");
        account.setAccountPassword(null);

        LocalValidatorFactoryBean local = (LocalValidatorFactoryBean) this.validator;

        BindingResult result = new BeanPropertyBindingResult(account, "account");

        local.validate(account, result);

        for (FieldError err : result.getFieldErrors()) LOGGER.debug(err.getField() +":" +err.toString());

        Assertions.assertTrue(result.hasErrors());

    }

    @Test
    void validate_invalidPassword_shouldBeNonValid() {

        Account account = new Account();
        account.setAccountPassword("AAAA");

        LocalValidatorFactoryBean local = (LocalValidatorFactoryBean) this.validator;

        BindingResult result = new BeanPropertyBindingResult(account, "account");

        local.validate(account, result);

        for (FieldError err : result.getFieldErrors()) LOGGER.debug(err.getField() +":" +err.toString());

        Assertions.assertTrue(result.hasErrors());
    }


    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    void setValidator(Validator validator) {
        this.validator = validator;
    }

}
