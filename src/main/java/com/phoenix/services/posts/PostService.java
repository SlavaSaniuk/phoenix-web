package com.phoenix.services.posts;

import com.phoenix.exceptions.NotPersistedEntity;
import com.phoenix.exceptions.NotPersistentEntityException;
import com.phoenix.models.Post;
import com.phoenix.models.User;

import java.util.List;

public interface PostService {

    /**
     * Methods create new Post entity, associate it with
     * {@link User} owner entity and save in database.
     * Method return created post entity with initializing "post_id" ID.
     * @param a_post - {@link Post} for saving.
     * @param a_owner - post user owner.
     * @return - created post with generated ID.
     */
    Post createPost(Post a_post, User a_owner) throws NotPersistedEntity;

    /**
     * Method retrieve all user_owner post entities from database.
     * Note: may be large result list.
     * @param a_owner - {@link User} posts owner.
     * @return - Resulting {@link List<Post>} list.
     * @throws NotPersistedEntity - if user entity don't have a defined (non-default) ID parameter.
     */
    List<Post> getUserPosts(User a_owner) throws NotPersistedEntity;

    /**
     * Method retrieve some user_owner post entities from database. Resulting list size
     * can be specified with "limit" parameter.
     * @param a_owner - {@link User} posts owner.
     * @param limit - size of resulting list.
     * @return - Resulting {@link List<Post>} list.
     * @throws NotPersistedEntity - if user entity don't have a defined (non-default) ID parameter.
     */
    List<Post> getSomeUserPosts(User a_owner, int limit) throws NotPersistedEntity;

    /**
     * Method retrieve some user_owner posts entities from database. Resulting list size
     * can be specified with "limit" parameter.
     * @param a_owner - {@link User} posts owner.
     * @param limit - How much posts to retrieve.
     * @return - Resulting {@link List<Post>} list;
     * @throws NotPersistentEntityException - if user entity not in persistence context or it's not detached.
     */
    List<Post> getSomeUserPostsFromTheEnd(User a_owner, int limit) throws NotPersistentEntityException;
}
