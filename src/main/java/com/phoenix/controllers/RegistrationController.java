package com.phoenix.controllers;

import com.phoenix.models.Account;
import com.phoenix.services.accounting.SigningService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    //Spring Beans
    private SigningService service; //autowired

    //Constructor
    @Autowired
    public RegistrationController(SigningService a_service) {
        LOGGER.info("Create " +getClass().getName() +" controller.");
        LOGGER.debug("Autowire " +a_service.getClass().getName() +" to " +getClass().getName() +" controller.");
        this.service = a_service;
    }


    @ModelAttribute("account")
    public Account createAccountModel() {
        return new Account();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/registration")
    public ModelAndView getRegistrationPage() {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("registration");

        return mav;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/registration")
    public String handleRegistrationRequest(@Valid @ModelAttribute("account")  Account account, BindingResult result) {



        //Validate form input
        if (result.hasFieldErrors()) {
            for (FieldError err : result.getFieldErrors())
                System.out.println(err.toString());
            LOGGER.error("ERRORS");
            return "registration"; //Exit from method

        }

        return "user";
    }

}
