package com.phoenix.services.users;

import com.phoenix.models.User;
import com.phoenix.models.UserDetail;

import javax.persistence.EntityNotFoundException;

/**
 * Common interface to manage {@link UserDetail} entity lifecycle. Service can register new entities in application.
 */
public interface DetailsService {

    /**
     * Associate newly created Details entity with created user entity and persist it in database.
     * @param detail - {@link UserDetail} filled entity.
     * @param user - {@link User} created user.
     * @return - generated UserDetails ID.
     * @throws EntityNotFoundException - if user entity not has been persisted before.
     */
    int registerNewDetail(UserDetail detail, User user) throws EntityNotFoundException;

}
