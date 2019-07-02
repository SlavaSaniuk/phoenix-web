package com.phoenix.controllers;

import com.phoenix.exceptions.EmailAlreadyRegisterException;
import com.phoenix.models.Account;
import com.phoenix.services.accounting.SigningService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;

@Controller
public class RegistrationController {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    //Spring Beans
    private SigningService service; //autowired via constructor
    private MessageSource msg_src; //autowired via setters
    private LocaleResolver locale_resolver; //autowired via setters

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
    public ModelAndView handleRegistrationRequest(@Valid @ModelAttribute("account")  Account account, BindingResult result,  HttpServletRequest req) {

        //Create mav
        ModelAndView mav = new ModelAndView();

        //Validate form input
        if (result.hasFieldErrors()) {
            LOGGER.debug("Account field validation failed");
            mav.setViewName("registration");
            return mav; //Exit from method
        }

        //Try to sign up
        int generated_id;
        try {
            generated_id = this.service.signUp(account);
        }catch (EmailAlreadyRegisterException exc) { //Catch if account already registered
            LOGGER.debug(exc.getMessage());
            Locale req_locale = this.locale_resolver.resolveLocale(req);
            String localized_err_msg = this.msg_src.getMessage("alreadyexist.Account.email", null, req_locale);
            ObjectError email_err = new ObjectError("account",  localized_err_msg);
            result.addError(email_err);
            mav.setViewName("registration");
            return mav; //Exit from method
        }


        mav.setViewName("redirect:/user_" +generated_id);
        return mav;
    }

    @Autowired
    @Qualifier("validationMessageSource")
    public void setMessageSource(MessageSource a_src) {
        LOGGER.debug("Autowire " +MessageSource.class.getName() + " to " +getClass().getName() +" HTTP controller.");
        this.msg_src = a_src;
    }

    @Autowired
    public void setLocaleResolver(LocaleResolver a_resolver){
        LOGGER.debug("Autowire " +LocaleResolver.class.getName() + " to " +getClass().getName() +" HTTP controller.");
        this.locale_resolver = a_resolver;
    }

}
