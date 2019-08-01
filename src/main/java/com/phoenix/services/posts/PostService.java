package com.phoenix.services.posts;

import com.phoenix.exceptions.NotPersistedEntity;
import com.phoenix.models.Post;
import com.phoenix.models.User;

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

}
