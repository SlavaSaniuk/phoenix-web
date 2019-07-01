package com.phoenix.controllers;

import com.phoenix.models.Account;
import com.phoenix.services.accounting.SigningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping(path = {"/registration", "registration.*"})
public class RegistrationController {

    //Spring Beans
    private SigningService service; //autowired

    @ModelAttribute(name = "account")
    public Account createAccountModel() {
        return new Account();
    }

    @GetMapping
    public ModelAndView getRegistrationPage() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("registration");
        return mav;
    }

    @PostMapping
    public ModelAndView handleRegistrationRequest(@Valid @ModelAttribute Account account, BindingResult result) {
        return null;
    }

    //Spring autowiring
    @Autowired
    public void setSigningService(SigningService a_service) {
        this.service = a_service;
    }
}
