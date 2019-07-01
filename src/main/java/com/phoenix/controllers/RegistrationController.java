package com.phoenix.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(path = {"/registration", "registration.*"})
public class RegistrationController {

    @GetMapping
    public ModelAndView getRegistrationPage() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("registration");
        return mav;
    }
}
