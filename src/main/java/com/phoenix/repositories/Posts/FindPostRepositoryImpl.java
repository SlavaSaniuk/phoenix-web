package com.phoenix.repositories.Posts;

import com.phoenix.models.Post;
import com.phoenix.models.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository

public class FindPostRepositoryImpl implements FindPostRepository {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(FindPostRepositoryImpl.class);

    @PersistenceContext
    private EntityManager em;

    //Default constructor
    public FindPostRepositoryImpl() {
        LOGGER.debug("Start to create " +getClass().getName() +" repository implementation bean.");
    }


    @Override
    public List<Post> findAllPostsByUser(User user) throws IllegalArgumentException {


    }

    @Override
    public List<Post> findSomePostsByUser(User user, int limit) throws IllegalArgumentException {

        //Check whether user is null
        if (user == null) throw new IllegalArgumentException("User parameter can not be null.");

        //Create query
        Query query = this.em.createQuery("SELECT p FROM Post p WHERE p.post_owner = :user_id");
        //Set parameters
        query.setParameter("user_id", user.getUserId());
        query.setMaxResults(limit);

        //Get list of post and return them

    }

}
