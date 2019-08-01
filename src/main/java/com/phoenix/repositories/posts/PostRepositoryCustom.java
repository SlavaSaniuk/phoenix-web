package com.phoenix.repositories.posts;

import com.phoenix.models.Post;
import com.phoenix.models.User;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

/**
 * Custom {@link Post} entity repository. Repository define methods for retrieve post entities
 * from database by user owner.
 */
@NoRepositoryBean
public interface PostRepositoryCustom {

    /**
     * Retrieve some post entities from database. Limit may be specified by define {@code int limit }  parameters.
     * @param owner - {@link User} owner.
     * @param limit - resulting list size.
     * @return - {@link List<Post>} of users post, or null - if {@link ClassCastException} occurs.
     */
    List<Post> findSomePostsByOwner(User owner, int limit);

    /**
     * Retrieve all post entities from database.
     * @param owner - {@link User} owner.
     * @return - {@link List<Post>} of users posts, or null - if {@link ClassCastException} occurs.     */
    List<Post> findAllPostsByOwner(User owner);
}
