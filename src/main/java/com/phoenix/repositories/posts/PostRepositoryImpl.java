package com.phoenix.repositories.posts;

import com.phoenix.exceptions.NotPersistentEntityException;
import com.phoenix.models.Post;
import com.phoenix.models.User;
import com.phoenix.services.utilities.JpaCaster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Default implementation of {@link PostRepositoryCustom} repository bean.
 */
@Repository
public class PostRepositoryImpl implements PostRepositoryCustom, InitializingBean {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(PostRepositoryImpl.class);

    @PersistenceContext
    private EntityManager em;

    //Default constructor
    public PostRepositoryImpl() {
        LOGGER.debug("Start to create " +getClass().getName() +" repository bean.");
    }

    @Override
    public List<Post> findSomePostsByOwner(User owner, int limit) {

        //Check whether user or user_id is null
        if (owner == null || owner.getUserId() == 0 ) throw new IllegalArgumentException("User ID parameter can't to be zero.");

        //Create query
        Query query = this.em.createQuery("SELECT p FROM Post p WHERE p.post_owner = :user");
        //Set parameters
        query.setParameter("user", owner);

        //Check whether limit lower than zero
        if (limit > 0) query.setMaxResults(limit);

        //Get list of post and return them
        List result = query.getResultList();

        //Cast resulting list to Post type
        return JpaCaster.castObjectsListToType(Post.class, result);

    }

    @Override
    public List<Post> findAllPostsByOwner(User owner) {
        return this.findSomePostsByOwner(owner, 0);
    }

    @Override
    public List<Post> findSomePostsByOwnerFromTheEnd(User owner, int limit) throws NotPersistentEntityException, NullPointerException {

        //Check whether owner parameter is not null
        if (owner == null) throw new NullPointerException(User.class.getName() +" parameter is null");

        //Check whether owner parameter is persistent
        if (owner.getUserId() == 0) throw new NotPersistentEntityException(owner.getClass());

        //Create JPQL query
        Query q = this.em.createQuery("SELECT p FROM Post p WHERE p.post_owner = :owner ORDER BY p.post_id DESC");
        q.setParameter("owner", owner);
        if (limit > 0) q.setMaxResults(limit);
        List founded = q.getResultList();

        //Cast result list
        return JpaCaster.castObjectsListToType(Post.class, founded);
    }

    @Override
    public List<Post> findAllPostEntitiesByUserFromTheEnd(User owner) throws NotPersistentEntityException {
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (this.em == null) {
            LOGGER.error("EntityManager in " +getClass().getName() +" not initializing.");
            throw new Exception(new BeanDefinitionStoreException("Not initializing " +EntityManager.class.getName() +" persistence bean."));
        }
    }
}
