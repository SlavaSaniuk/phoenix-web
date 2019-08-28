package com.phoenix.services.authorization;

import com.phoenix.models.relation.users.User;
import javax.servlet.http.HttpSession;

public interface Authorization {

    /**
     * Authorize given user in application runtime. Method put users entity
     * in session attribute and set 'authenticated' flag to true.
     * @param user - {@link User} entity to authorize.
     * @param session - Given user {@link HttpSession} session.
     */
    void authorize(User user, HttpSession session);

    /**
     * Deautorize user in application runtime. Method terminate user session.
     * @param session - user {@link HttpSession} session.
     */
    void deAuthorize(HttpSession session);
}
