package com.phoenix.services.posts;

import com.phoenix.exceptions.NotPersistedEntity;
import com.phoenix.models.Post;
import com.phoenix.models.User;
import com.phoenix.repositories.posts.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service("PostService")
public class PostServiceImpl implements PostService, InitializingBean {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(PostServiceImpl.class);

    //Spring beans
    private PostRepository repository; //Autowired via constructor

    //Constructor
    public PostServiceImpl(PostRepository repository) {
        LOGGER.debug("Start to create " +getClass().getName() +" service implementation bean.");

        LOGGER.debug("Autowire: " +repository.getClass().getName() +" to " +getClass().getName());
        this.repository = repository;
    }

    @Override
    public Post createPost(Post a_post, User a_owner) throws NotPersistedEntity {

        //Check whether parameters are non null
        if (a_owner == null || a_post == null) throw new IllegalArgumentException("One of parameters is null.");

        //Check whether user entity persisted before
        if (a_owner.getUserId() == 0) throw new NotPersistedEntity(a_owner);

        //Set user owner
        a_post.setPostOwner(a_owner);

        //Persist post
        Post persisted = this.repository.save(a_post);

        //Set post to user post list
        a_owner.getUserPosts().add(0, persisted);

        return persisted;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.repository == null) {
            LOGGER.error("Not initializing bean:  " +PostRepository.class.getName() +" in " +getClass().getName() +" service bean.");
            throw new Exception(new BeanDefinitionStoreException("Not Initializing " +PostRepository.class.getName() +" repository bean."));
        }
    }
}
