package com.phoenix.controllers;

import com.phoenix.models.relation.users.User;
import com.phoenix.models.wrappers.UserWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/my_page")
public class MyPageController {

    @GetMapping
    public ModelAndView getMyPage(@SessionAttribute("current_user") User current_user) {

        ModelAndView mav = new ModelAndView();

        //Check whether user is authenticated
        if (current_user == null) {
            mav.setViewName("login");
            return mav;
        }

        mav.getModel().put("current_wrapper", new UserWrapper(current_user));
        mav.setViewName("users/my_page");
        return mav;
    }

}
