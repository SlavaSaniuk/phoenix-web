package com.phoenix.controllers;

import com.phoenix.exceptions.NotPersistedEntity;
import com.phoenix.models.Post;
import com.phoenix.models.User;
import com.phoenix.models.forms.PostForm;
import com.phoenix.services.posts.PostService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MyPageController {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(MyPageController.class);
    //Spring beans
    private PostService service; //Autowired via setter

    @GetMapping(value = "/my_page")
    public ModelAndView getMyPage(@SessionAttribute("current_user") User current_user) {

        ModelAndView mav = new ModelAndView();

        //Check whether user is authenticated
        if (current_user == null) {
            mav.setViewName("login");
            return mav;
        }

        //Create post model object
        mav.getModel().put("post_form", new PostForm());

        //Get user posts
        try {
            List<Post> user_posts = this.service.getSomeUserPosts(current_user, 5);
            List<PostForm> posts_forms = new ArrayList<>();
            for (Post p : user_posts) {
                posts_forms.add(new PostForm(p));
            }
            mav.getModel().put("user_posts", posts_forms);
        }catch (NotPersistedEntity exc) {
            LOGGER.debug(exc.getMessage());
            mav.setViewName("redirect:/login");
            return mav;
        }

        mav.setViewName("users/my_page");
        return mav;
    }

    @PostMapping(value = "/createPost")
    public ModelAndView postCreationRequest(@ModelAttribute("post_form") PostForm form, @SessionAttribute("current_user") User current_user) {

        ModelAndView mav = new ModelAndView();

        //Check whether user is authenticated
        if (current_user == null) {
            mav.setViewName("login");
            return mav;
        }

        //Validate post form

        //Create post
        Post post = form.createPost();
        this.service.createPost(post, current_user);

        //return
        mav.setViewName("redirect:/user_" +current_user.getUserId());
        return mav;
    }

    @Autowired
    public void setPostService(PostService a_service) {
        LOGGER.debug("Autowire: " +a_service.getClass().getName() +" to " +getClass().getName() +" controller bean.");
        this.service = a_service;
    }


}
