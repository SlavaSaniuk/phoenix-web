package com.phoenix.services.users;

import com.phoenix.models.User;
import com.phoenix.models.UserDetail;

import javax.persistence.EntityNotFoundException;

public interface DetailsService {

    UserDetail registerNewDetail(UserDetail detail, User user) throws EntityNotFoundException;

}
