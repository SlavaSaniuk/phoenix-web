package com.phoenix.controllers;

import com.phoenix.exceptions.EmailAlreadyRegisterException;
import com.phoenix.exceptions.JpaEngineException;
import com.phoenix.models.relation.users.User;
import com.phoenix.models.forms.RegistrationForm;


import com.phoenix.services.accounting.SigningService;
import com.phoenix.services.authorization.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;


/**
 * {@link RegistrationController} web http controller which handle GET/POST request on "/registration" URL.
 * {@link RegistrationController#getRegistrationPage()} set to rendering "registration.html" page, and
 *  register users in application.
 */
@Controller
public class RegistrationController implements InitializingBean {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    //Spring beans
    private SigningService service; //Autowired via constructor
    private Authorization cas; //Autowired via setter
    private MessageSource error_message_source; //Autowired via setters
    private LocaleResolver resolver; //Autowired via setters

    /**
     * Spring automatically create registration controller bean and
     * autowire {@link SigningService} service bean to them. Service used to
     * register user in application.
     * @param a_service {@link SigningService} registration service bean.
     */
    //Constructor
    public RegistrationController(SigningService a_service) {
        LOGGER.info("Create " +getClass().getName() +" controller bean.");

        LOGGER.debug("Autowire: " +a_service.getClass().getName() + " to " +getClass().getName() +" controller bean");
        this.service = a_service;
    }

    /**
     * {@link RegistrationForm} model attribute used by thymeleaf to set users inputs to this form fields.
     * Model attribute with empty fields send as request attribute in HTTP get request on this controller.
     * With POST HTTP request this model attribute hold users inputs.
     * @return - new {@link RegistrationForm} form object.
     */
    @ModelAttribute("registration")
    public RegistrationForm createRegistrationForm() {
        return new RegistrationForm();
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
     * Register user in application. Method get filled {@link RegistrationForm} and validate them.
     * If form has fields error, {@link BindingResult} has this errors and method return user back
     * to registration page. If user input are valid methods try to register new user in application.
     * @param form - Filled {@link RegistrationForm} users registration form.
     * @param result - {@link BindingResult} hold validation errors.
     * @param req - {@link HttpServletRequest) used to define user locale, and return localized error
     * message if user email already registered.
     * @return {@link ModelAndView} object which redirect user to it's own user page if form fields are valid, or else
     * return back to registration with fields errors.
     */
    @RequestMapping(method = RequestMethod.POST, path = "/registration")
    public ModelAndView handleRegistrationRequest(@Valid @ModelAttribute("registration") RegistrationForm form, BindingResult result, HttpServletRequest req) {

        //Create mav
        ModelAndView mav = new ModelAndView();

        //Check on fields errors
        if (result.hasFieldErrors()) {
            mav.setViewName("registration");
            return mav;
        }

        //Try to register user
        User cuurent_user;
        try {
            cuurent_user = this.service.signUp(form);
        }catch (EmailAlreadyRegisterException exc) {
            LOGGER.debug(exc.toString());

            //Add error field
            Locale req_locale = this.resolver.resolveLocale(req);
            String error_msg = this.error_message_source.getMessage("registered.Account.email", null, "Email already registered", req_locale);
            result.addError(new FieldError("form", "email", error_msg));

            //Return back
            mav.setViewName("registration");
            return mav;

        }catch (JpaEngineException exc) {
            LOGGER.error(exc.toString());
            mav.setViewName("error");
            return mav;
        }

        //Authorize user
        this.cas.authorize(cuurent_user, req.getSession(true));

        //Return mav
        mav.setViewName("redirect:/user_" +cuurent_user.getUserId());
        return mav;
    }

    /**
     * Validate this spring beans fields. Check whether this fields has been initialized.
     * @throws Exception - If {@link SigningService} service bean has been not autowired.
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.service == null) {
            LOGGER.error("Not initializing " +SigningService.class.getName() +" in " +getClass().getName() +" controller bean.");
            throw new Exception(new BeanDefinitionStoreException("Not initializing " +this.service.getClass().getName() +" service bean."));
        }

        if(this.error_message_source == null) {
            LOGGER.warn("Not initialized " +MessageSource.class.getName() +" in " +getClass().getName() +" controller bean.");
            LOGGER.warn("Validation fields will be not localized");
        }

        if (this.resolver == null) {
            LOGGER.warn("Not initialized " + LocaleResolver.class.getName() +" in " +getClass().getName() +" controller bean.");
            LOGGER.warn("Validation fields will be not localized");
        }
    }

    @Autowired(required = false)
    private void autowire(@Qualifier("ValidationSource") MessageSource src,  LocaleResolver resolver) {
        this.error_message_source = src;
        this.resolver = resolver;
    }

    @Autowired
    private void setCas(Authorization authorization) {
        LOGGER.debug("Autowire: " +authorization.getClass().getName() + " to " +getClass());
        this.cas = authorization;
    }
}
