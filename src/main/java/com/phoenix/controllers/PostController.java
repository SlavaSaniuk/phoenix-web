package com.phoenix.controllers;

import com.phoenix.models.Post;
import com.phoenix.models.User;
import com.phoenix.models.forms.PostForm;
import com.phoenix.services.posts.PostService;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class PostController {

    //Spring beans
    private PostService service;

    public String createPostRequest(@Valid @ModelAttribute("post") PostForm form, BindingResult result, @SessionAttribute("current_user") User user) {

        //Check whether user is non null
        if (user == null) {
            //redirect to login page
            return "redirect:/login";
        }

        //Create a post
        Post post = form.createPost();
        //Persist it
        this.service.createPost(post, user);

        //Redirect to user page
        return "redirect:/user_" +user.getUserId();
    }

}
