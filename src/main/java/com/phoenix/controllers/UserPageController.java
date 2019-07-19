package com.phoenix.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserPageController {

    @RequestMapping(method = RequestMethod.GET, path = "/user_{user_id}")
    public ModelAndView getUserPage(@PathVariable("user_id") int user_id) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("users/user_page");
        return mav;
    }

}
