package com.phoenix.controllers;

import com.phoenix.models.forms.RegistrationForm;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.servlet.ModelAndView;



/**
 * {@link RegistrationController} web http controller which handle GET/POST request on "/registration" URL.
 * {@link RegistrationController#getRegistrationPage()} set to rendering "registration.html" page, and
 *  register users in application.
 */
@Controller
public class RegistrationController {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

    //Constructor
    public RegistrationController() {
        LOGGER.info("Create " +getClass().getName() +" controller.");
    }

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


    @RequestMapping(method = RequestMethod.POST, path = "/registration")
    public ModelAndView handleRegistrationRequest(@ModelAttribute("registration") RegistrationForm form, BindingResult result) {

        //Create mav
        ModelAndView mav = new ModelAndView();





        //Return mav
        mav.setViewName("registration");
        return mav;
    }

}
