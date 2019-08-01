package com.phoenix.repositories.posts;

import com.phoenix.models.Post;
import com.phoenix.models.User;
import com.phoenix.services.utilities.JpaCaster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class PostRepositoryImpl implements PostRepositoryCustom {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(PostRepositoryImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Post> findSomePostsByOwner(User owner, int limit) {

        //Check whether user or user_id is null
        if (owner == null || owner.getUserId() == 0 ) throw new IllegalArgumentException("User ID parameter can not be zero.");

        //Create query
        Query query = this.em.createQuery("SELECT p FROM Post p WHERE p.post_owner = :user");
        //Set parameters
        query.setParameter("user", owner);
        query.setMaxResults(limit);

        //Get list of post and return them
        List result = query.getResultList();
        try {
            return JpaCaster.castObjectsListToType(Post.class, result);
        }catch (ClassCastException exc) {
            LOGGER.error(exc.toString());
            return null;
        }

    }

    @Override
    public List<Post> findAllPostsByOwner(User owner) {
        return this.findSomePostsByOwner(owner, Integer.MAX_VALUE);
    }
}
