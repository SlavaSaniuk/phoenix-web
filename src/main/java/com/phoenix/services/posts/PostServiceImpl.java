package com.phoenix.services.posts;

import com.phoenix.exceptions.NotPersistedEntity;
import com.phoenix.exceptions.NotPersistentEntityException;
import com.phoenix.models.Post;
import com.phoenix.models.User;
import com.phoenix.repositories.posts.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("PostService")
public class PostServiceImpl implements PostService, InitializingBean {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(PostServiceImpl.class);

    //Spring beans
    private PostRepository repository; //Set in constructor

    //Constructor
    public PostServiceImpl(PostRepository repository) {
        LOGGER.debug("Start to create " +getClass().getName() +" service implementation bean.");

        LOGGER.debug("Autowire: " +repository.getClass().getName() +" to " +getClass().getName());
        this.repository = repository;
    }

    @Override
    @Transactional
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
        //a_owner.getUserPosts().add(0, persisted);

        return persisted;
    }

    @Override
    public List<Post> getUserPosts(User a_owner) throws NotPersistedEntity {

        //Check input parameters
        if (a_owner == null) throw new NullPointerException("User entity parameter is null.");
        if (a_owner.getUserId() == 0) throw new NotPersistedEntity(a_owner);

        //Get posts
        return this.repository.findAllPostsByOwner(a_owner);

    }

    @Override
    public List<Post> getSomeUserPosts(User a_owner, int limit) throws NotPersistedEntity {

        //Check input parameters
        if (a_owner == null) throw new NullPointerException("User entity parameter is null");
        if (a_owner.getUserId() == 0) throw new NotPersistedEntity(a_owner);
        if (limit <= 0) throw new IllegalArgumentException("Limit parameter less or equals zero");

        //Get posts
        return this.repository.findSomePostsByOwner(a_owner, limit);

    }

    @Override
    public List<Post> getSomeUserPostsFromTheEnd(User a_owner, int limit) throws NotPersistentEntityException {

        if (a_owner == null) throw new NullPointerException(User.class.getName() +" parameter is null.");
        if (a_owner.getUserId() == 0) throw new NotPersistentEntityException(a_owner.getClass());

        if (limit <= 0) throw new IllegalArgumentException("'limit' parameter must be more than zero.");

        return this.repository.findSomePostsByOwnerFromTheEnd(a_owner, limit);

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.repository == null) {
            LOGGER.error("Not initializing bean:  " +PostRepository.class.getName() +" in " +getClass().getName() +" service bean.");
            throw new Exception(new BeanDefinitionStoreException("Not Initializing " +PostRepository.class.getName() +" repository bean."));
        }
    }
}
