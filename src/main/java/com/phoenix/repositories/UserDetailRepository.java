package com.phoenix.repositories;

import com.phoenix.models.UserDetail;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Simple CRUD repository for {@link UserDetail} entity. Repository implement {@link CrudRepository} interface,
 * which contain simple CRUD operations. Spring automatically create implementation of this repository interface.
 */
@Repository
public interface UserDetailRepository extends CrudRepository<UserDetail, Integer> {

}
