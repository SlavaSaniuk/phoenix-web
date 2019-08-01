package com.phoenix.repositories.posts;

import com.phoenix.models.Post;
import org.springframework.data.repository.CrudRepository;

/**
 * Common {@link Post} entity CRUD repository. Repository extends
 * spring {@link CrudRepository} interface and custom {@link PostRepositoryCustom} interface
 * with defined custom find methods.
 */
public interface PostRepository extends CrudRepository<Post, Long>, PostRepositoryCustom  {
}
