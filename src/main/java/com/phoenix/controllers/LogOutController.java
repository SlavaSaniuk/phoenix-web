package com.phoenix.controllers;

import com.phoenix.services.authorization.Authorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LogOutController {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(LogOutController.class);
    //Spring beans
    private Authorization authorizator; //Autowired in constructor

    //Constructor
    @Autowired
    public LogOutController(Authorization authorization) {
        LOGGER.info("Create " +getClass().getName() +" controller bean.");

        LOGGER.debug("Autowire: " +authorization.getClass().getName() +" to " +getClass().getName() +" controller bean");
        this.authorizator = authorization;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/logout")
    public String handleLogOutRequest(HttpServletRequest req) {

        //Get user session
        HttpSession session = req.getSession();
        //De authorize user
        this.authorizator.deAuthorize(session);

        //Redirect to login page
        return "redirect:/login";
    }
}
