package com.phoenix.models.wrappers;

import com.phoenix.models.Post;
import com.phoenix.models.User;
import java.util.HashSet;
import java.util.Set;

public class PostsWrapper extends Wrapper {

    //User posts
    private final Set<Post> user_posts = new HashSet<>();
    private boolean isInitialized = false; //Initialization flag


    public PostsWrapper(User user) {
        super(user);
    }




}
