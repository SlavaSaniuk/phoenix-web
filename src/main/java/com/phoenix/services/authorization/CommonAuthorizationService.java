package com.phoenix.services.authorization;

import com.phoenix.models.relation.users.User;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service
public class CommonAuthorizationService implements Authorization, InitializingBean {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonAuthorizationService.class);

    //Constructor
    public CommonAuthorizationService() {
        LOGGER.debug("Start to create " +getClass().getName() +" service bean.");
    }

    @Override
    public void authorize(User user, HttpSession session) throws IllegalArgumentException {

        //Check whether users session is created
        if (session == null) throw new IllegalArgumentException("User session parameter is null.");

        //Authorize user
        if (user == null) {
            session.setAttribute("authenticated", false);
            session.setAttribute("current_user", new User());
        }else {
            session.setAttribute("authenticated", true);
            session.setAttribute("current_user", user);
        }

    }

    @Override
    public void deAuthorize(HttpSession session) {

        //Check whether session is exist
        if (session == null) throw new IllegalArgumentException("Session object is null");

        //invalidate session
        session.invalidate();
    }

    @Override
    public void afterPropertiesSet() {
        LOGGER.debug(getClass().getName() +" service bean was created.");
    }
}
