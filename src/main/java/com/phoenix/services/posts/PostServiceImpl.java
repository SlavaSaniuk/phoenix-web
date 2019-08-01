package com.phoenix.services.posts;

import com.phoenix.exceptions.NotPersistedEntity;
import com.phoenix.models.Post;
import com.phoenix.models.User;
import com.phoenix.repositories.posts.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("PostService")
public class PostServiceImpl implements PostService, InitializingBean {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(PostServiceImpl.class);

    //Spring beans
    private PostRepository repository; //Autowired via constructor

    //Constructor
    @Autowired
    public PostServiceImpl(PostRepository repository) {
        LOGGER.debug("Start to create " +getClass().getName() +" service implementation bean.");

        LOGGER.debug("Autowire: " +repository.getClass().getName() +" to " +getClass().getName());
        this.repository = repository;
    }

    @Override
    public Post createPost(Post a_post, User a_owner) {

        //Check whether parameters are not null
        if (a_post == null || a_owner == null) throw new IllegalArgumentException("One of parameters is null.");

        //Check whether owner has an ID
        if (a_owner.getUserId() == 0) throw new NotPersistedEntity(a_owner);

        //Set post owner
        a_post.setPostOwner(a_owner);
        //Add post to user posts
        a_owner.getUserPosts().add(a_post);

        //Save and return post entity
        return this.repository.save(a_post);
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
