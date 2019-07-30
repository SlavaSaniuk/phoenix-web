package com.phoenix.repositories.Posts;

import com.phoenix.models.Post;
import com.phoenix.models.User;
import java.util.List;
import org.springframework.lang.NonNull;

public interface FindPostRepository {

    List<Post> findAllPostsByUser(@NonNull User user) throws IllegalArgumentException;

    List<Post> findSomePostsByUser(@NonNull User user, int limit) throws IllegalArgumentException;
}
