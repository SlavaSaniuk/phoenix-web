package com.phoenix.services.authorization;

import com.phoenix.models.User;
import javax.servlet.http.HttpSession;

public interface Authorization {

    /**
     * Authorize given user in application runtime. Method put users entity
     * in session attribute and set 'authenticated' flag to true.
     * @param user - {@link User} entity to authorize.
     * @param session - Given user {@link HttpSession} session.
     */
    void authorizate(User user, HttpSession session);


}
