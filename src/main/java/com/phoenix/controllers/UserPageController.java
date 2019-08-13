package com.phoenix.controllers;

import com.phoenix.models.Post;
import com.phoenix.models.User;
import com.phoenix.models.forms.PostForm;
import com.phoenix.models.wrappers.PostsWrapper;
import com.phoenix.repositories.UserRepository;
import com.phoenix.services.posts.PostService;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class UserPageController implements InitializingBean {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(UserPageController.class);
    //Spring beans
    private UserRepository repository; //Autowired via constructor
    private PostService post_service; //Autowired via setter

    //Constructor
    @Autowired
    public UserPageController(UserRepository repository) {
        LOGGER.info("Create " +getClass().getName() +" controller bean");

        LOGGER.debug("Autowire: " +repository.getClass().getName() +" to " +getClass().getName() +" controller bean");
        this.repository = repository;
    }

    /*
      *** Model attributes ***
     */

    /**
     * Model bean to create user's posts.
     * @return - empty {@link PostForm} model bean.
     */
    @ModelAttribute("post_form")
    public PostForm modelPostForm() {
        return new PostForm();
    }


    @RequestMapping(method = RequestMethod.GET, path = "/user_{user_id}")
    public ModelAndView getUserPage(@PathVariable("user_id") int user_id, HttpSession user_session) {

        //Create model and view object
        ModelAndView mav = new ModelAndView();

        //Get "current_user" session attribute
        User current_user = (User) user_session.getAttribute("current_user");

        //Check whether user want to access your own page
        if (current_user.getUserId() == user_id) {

            //User want to access your own page

            //Posts model
            //Access via session attribute
            //Check whether post wrapper is initialized
            PostsWrapper posts_wrapper = (PostsWrapper) user_session.getAttribute("posts_wrapper");
            if (!posts_wrapper.isInitialized()) {
                //if not initialized -> initialize with basic(minimal) size
                List<Post> users_posts = this.post_service.getSomeUserPostsFromTheEnd(current_user, 5);
                posts_wrapper.addSomePosts(users_posts);
            }

            //View name
            mav.setViewName("users/my_page");

        }else {

            //User want to access other users page
            //Get user from database
            Optional<User> given_user_opt = this.repository.findById(user_id);
            if (!given_user_opt.isPresent()) throw new EntityNotFoundException("404");

            User given_user = given_user_opt.get();

            //Posts model
            //Access via model attribute
            List<Post> given_user_posts = this.post_service.getSomeUserPostsFromTheEnd(given_user, 5);
            mav.getModel().put("users_posts", given_user_posts);

            //View name
            mav.setViewName("users/user_page");
        }

        return mav;
    }

    @PostMapping(path = "/create_post")
    public ModelAndView createPostRequest(@Valid @ModelAttribute("post_form") PostForm post_form,
                                          @SessionAttribute("current_user") User current_user, @SessionAttribute("posts_wrapper") PostsWrapper posts_wrapper) {

        ModelAndView mav = new ModelAndView();

        //Validate post form
        //...

        //Create post from post form
        Post p = post_form.createPost();

        //Save post
        p = this.post_service.createPost(p, current_user);
        //Add it to posts wrapper
        posts_wrapper.addPost(p);

        mav.setViewName("redirect:/user_" +current_user.getUserId());
        return mav;
    }







    @Autowired
    private void setPostService(PostService a_service) {
        LOGGER.info("Autowire " +a_service.getClass().getName() + " to " +getClass().getName() +" controller bean.");
        this.post_service = a_service;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.repository == null) {
            LOGGER.error("Not initializing " + UserRepository.class.getName() +" repository bean in " +getClass().getName() +" service bean");
            throw new Exception(new BeanDefinitionStoreException(UserRepository.class.getName() +" is not set"));
        }
    }
}
