package com.phoenix.repositories;

import com.phoenix.models.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Simple CRUD repository for User entity. Repository implement {@link CrudRepository} interface,
 * which contain simple CRUD operations. Spring automatically create implementation of this repository interface.
 */
public interface UserRepository extends CrudRepository<User, Integer> {
}
