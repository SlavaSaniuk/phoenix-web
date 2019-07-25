package com.phoenix.controllers;

import com.phoenix.exceptions.JpaEngineException;
import com.phoenix.models.User;
import com.phoenix.models.forms.LoginForm;
import com.phoenix.services.accounting.SigningService;
import com.phoenix.services.authorization.Authorization;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * {@link LoginController} class is a web controller class (annotated with {@link Controller} annotation)
 * which return view name of login page (login.html) and handing login request (POST method).
 */
@Controller
@RequestMapping("/login")
public class LoginController implements InitializingBean {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    //Spring beans
    private SigningService service; //Autowired in constructor
    private Authorization authorization; //Autowired via setter

    public LoginController(SigningService a_service) {
        LOGGER.info("Create " +getClass().getName() +" controller bean.");
        LOGGER.debug("Autowire: " +a_service.getClass().getName() +" to " +getClass().getName() +" controller bean");
        this.service = a_service;
    }

    @ModelAttribute("form")
    public LoginForm createLoginForm() {
        return new LoginForm();
    }

    /**
     * Method handing "GET" request to '/login' URL and return
     * login page view name.
     * @return - Login page view name ("login").
     */
    @GetMapping
    public ModelAndView getLoginPage() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("login");

        return mav;
    }


    @PostMapping
    public ModelAndView handleLoginRequest(@Valid @ModelAttribute("form") LoginForm form, BindingResult result, HttpServletRequest request) {

        ModelAndView mav = new ModelAndView();

        //Validate user input
        if (result.hasFieldErrors()) {
            mav.setViewName("login");
            return mav;
        }

        //Try to sign in
        User user;
        try {
            user = this.service.signIn(form.createAccount());
        }catch (IllegalArgumentException | JpaEngineException exc) {
            LOGGER.error(exc.getMessage());
            mav.setViewName("redirect:/error");
            return mav;
        }

        if (user == null) {
            result.addError(new FieldError("form", "email", "Email or password is incorrect"));
            mav.setViewName("login");
            return mav;
        }

        //Authorize user
        try {
            this.authorization.authorize(user, request.getSession());
        }
        catch (IllegalArgumentException exc) {
            LOGGER.warn(exc.getMessage());
            HttpSession session = request.getSession(true);
            this.authorization.authorize(user, session);
        }

        mav.setViewName("redirect:/user_" +user.getUserId());
        return mav;
    }

    @Autowired
    public void setAuthorization(Authorization authorization) {
        LOGGER.debug("Autowire: " +authorization.getClass().getName() + " to " +getClass().getName() +" controller bean.");
        this.authorization = authorization;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.service == null) {
            LOGGER.error("Not initializing " +SigningService.class.getName() +" in " +getClass().getName() +" controller bean.");
            throw new Exception(new BeanDefinitionStoreException("Not initializing " +this.service.getClass().getName() +" service bean."));
        }

        if (this.authorization == null) {
            LOGGER.error("Not initializing " +Authorization.class.getName() +" in " +getClass().getName() +" controller bean.");
            throw new Exception(new BeanDefinitionStoreException("Not initializing " +this.authorization.getClass().getName() +" service bean."));
        }
    }
}
