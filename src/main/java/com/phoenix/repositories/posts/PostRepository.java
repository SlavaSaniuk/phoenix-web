package com.phoenix.repositories.posts;

import com.phoenix.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long>, PostRepositoryCustom  {
}
