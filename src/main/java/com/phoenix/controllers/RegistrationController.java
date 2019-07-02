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

/**
 * {@link RegistrationController} web http controller which handle GET/POST request on "/registration" URL.
 * {@link RegistrationController#getRegistrationPage()} set to rendering "registration.html" page, and
 * {@link RegistrationController#handleRegistrationRequest(Account, BindingResult, HttpServletRequest)} register users in application.
 */
@Controller
public class RegistrationController {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    //Spring Beans
    private SigningService service; //autowired via constructor
    private MessageSource msg_src; //autowired via setters
    private LocaleResolver locale_resolver; //autowired via setters

    /**
     * Default constructor. Used to log about its creation and mapping {@link SigningService} bean.
     * @param a_service - {@link SigningService} bean implementation (Default: {@link com.phoenix.services.accounting.SignAuthenticator}).
     */
    //Constructor
    @Autowired
    public RegistrationController(SigningService a_service) {
        LOGGER.info("Create " +getClass().getName() +" controller.");
        LOGGER.debug("Autowire " +a_service.getClass().getName() +" to " +getClass().getName() +" controller.");
        this.service = a_service;
    }

    /**
     * {@link Account} entity. Used to set email and password fields in registration view.
     * {@link RegistrationController#getRegistrationPage()} contains this entity as model attribute, and
     * {@link RegistrationController#handleRegistrationRequest(Account, BindingResult, HttpServletRequest)} get it's from POST request.
     * @return - empty account entity.
     */
    @ModelAttribute("account")
    public Account createAccountModel() {
        return new Account();
    }

    /**
     * Method handle {@link RequestMethod#GET} request on "/registration" url. Simply contain in your
     * {@link ModelAndView} view name (registration) to render registration form.
     * @return {@link ModelAndView} with "registration" view name.
     */
    @RequestMapping(method = RequestMethod.GET, path = "/registration")
    public ModelAndView getRegistrationPage() {

        ModelAndView mav = new ModelAndView();
        mav.setViewName("registration");

        return mav;
    }

    /**
     * Method handle {@link RequestMethod#POST} request on "/registration" URL. {@link javax.validation.Validator} validate {@link Account} parameter
     * and if parameter is not valid return back to registration page. {@link BindingResult} contains all errors if account parameters is non valid,
     * or {@link Account#getAccountEmail()} already registered. {@link HttpServletRequest} contains user locale in his {@link javax.servlet.http.HttpSession} field.
     * @param account - {@link Account} user account to register.
     * @param result {@link BindingResult} result of account validation.
     * @param req - {@link HttpServletRequest} user POST request on this URL
     * @return {@link ModelAndView} with "user" view name, or "registration" if account is not valid.
     */
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

    /**
     * Spring autowiring method to set {@link MessageSource} in validator.
     * @param a_src - validation {@link MessageSource}.
     */
    @Autowired
    @Qualifier("validationMessageSource")
    public void setMessageSource(MessageSource a_src) {
        LOGGER.debug("Autowire " +MessageSource.class.getName() + " to " +getClass().getName() +" HTTP controller.");
        this.msg_src = a_src;
    }

    /**
     * Spring autowiring to set {@link org.springframework.web.servlet.i18n.SessionLocaleResolver} bean
     * {@link LocaleResolver} resolve user locale on {@link HttpServletRequest} parameter.
     * @param a_resolver - Configured {@link LocaleResolver}
     */
    @Autowired
    public void setLocaleResolver(LocaleResolver a_resolver){
        LOGGER.debug("Autowire " +LocaleResolver.class.getName() + " to " +getClass().getName() +" HTTP controller.");
        this.locale_resolver = a_resolver;
    }

}
