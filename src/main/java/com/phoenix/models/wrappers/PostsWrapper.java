package com.phoenix.models.wrappers;

import com.phoenix.models.Post;
import com.phoenix.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class PostsWrapper extends Wrapper {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(PostsWrapper.class);

    //User posts
    private final List<Post> user_posts = new ArrayList<>();
    private boolean initialized = false; //Initialization flag

    //Constructor
    public PostsWrapper(User user) {
        super(user);
    }

    /**
     * Return "initialized" flag.
     * @return - {@link Boolean} true - if this wrapper contains a user posts, false - in other wise.
     */
    public boolean isInitialized() {
        return initialized;
    }

    public void addSomePosts(Collection<Post> posts) {

        //Add users posts
        this.user_posts.addAll(posts);

        //Change initialized flag
        this.initialized = true;

    }

    public void addPost(Post post) {
        this.user_posts.add(0, post);
    }

    //Getters
    public List<Post> getUsersPosts() {
        return this.user_posts;
    }





}
