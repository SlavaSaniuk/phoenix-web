package com.phoenix.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * {@link LoginController} class is a web controller class (annotated with {@link Controller} annotation)
 * which return view name of login page (login.html) and handing login request (POST method).
 */
@Controller
@RequestMapping("/login")
public class LoginController {

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
}
