package com.phoenix.services.posts;

import com.phoenix.exceptions.NotPersistedEntity;
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

    List<Post> getSomeUserPosts(User a_owner, int limit) throws NotPersistedEntity;


}
