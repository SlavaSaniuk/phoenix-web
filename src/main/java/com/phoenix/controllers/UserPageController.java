package com.phoenix.controllers;

import com.phoenix.models.User;
import com.phoenix.models.wrappers.UserWrapper;
import com.phoenix.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class UserPageController implements InitializingBean {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(UserPageController.class);
    //Spring beans
    private UserRepository repository; //Autowired via constructor

    //Constructor
    @Autowired
    public UserPageController(UserRepository repository) {
        LOGGER.info("Create " +getClass().getName() +" controller bean");

        LOGGER.debug("Autowire: " +repository.getClass().getName() +" to " +getClass().getName() +" controller bean");
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/user_{user_id}")
    public ModelAndView getUserPage(@PathVariable("user_id") int user_id, @SessionAttribute("current_user") User current_user) {

        ModelAndView mav = new ModelAndView();

        //Get user by ID
        Optional<User> given_user_optional = this.repository.findById(user_id);
        if (!given_user_optional.isPresent()) throw new IllegalArgumentException("Can't to find user by ID: " +user_id);
        User given_user = given_user_optional.get();

        //Check whether user want to access it's own page
        if (user_id == current_user.getUserId()) {
            mav.setViewName("forward:/my_page");
            return mav;
        }

        //Create user wrapper
        mav.getModel().put("given_wrapper", new UserWrapper(given_user));

        mav.setViewName("users/user_page");
        return mav;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.repository == null) {
            LOGGER.error("Not initializing " + UserRepository.class.getName() +" repository bean in " +getClass().getName() +" service bean");
            throw new Exception(new BeanDefinitionStoreException(UserRepository.class.getName() +" is not set"));
        }
    }
}
