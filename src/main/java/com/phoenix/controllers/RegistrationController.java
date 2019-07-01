package com.phoenix.controllers;

import com.phoenix.models.Account;
import com.phoenix.services.accounting.SigningService;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView handleRegistrationRequest(@Valid @ModelAttribute("account")  Account account, BindingResult result) {

        //Create mav
        ModelAndView mav = new ModelAndView();

        //Validate form input
        if (result.hasFieldErrors()) {
            LOGGER.debug("Account field validation failed");
            mav.setViewName("registration");
            return mav; //Exit from method
        }

        mav.setViewName("user");
        return mav;
    }

}
