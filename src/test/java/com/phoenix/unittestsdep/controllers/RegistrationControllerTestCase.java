package com.phoenix.unittestsdep.controllers;

import com.phoenix.controllers.RegistrationController;
import com.phoenix.services.accounting.SigningService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

class RegistrationControllerTestCase {

    @Mock
    SigningService service;

    @InjectMocks
    RegistrationController controller;

    //Validator
    private static Validator validator;

    @BeforeAll
    static void setUpBeforeAll() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator =  factory.getValidator();
    }

    @Test
    void handleRegistrationAccount_notValidAccount_shouldBeConstraintViolation() {

    }

    @Test
    void getRegistrationPage_newRequest_shouldReturnExpectedViewName() {
        Assertions.assertEquals("registration", this.controller.getRegistrationPage().getViewName());
    }
}
