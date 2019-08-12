package com.phoenix.models.wrappers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component("UserWrapper")
@Scope(WebApplicationContext.SCOPE_SESSION)
public class UserWrapper extends Wrapper {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(UserWrapper.class);

    //Constructors
    public UserWrapper() {
        super();

        //Log
        LOGGER.debug("Create " +getClass().getName() +" (session scoped) wrapper bean.");
    }

    /**
     * Method return user first and last name ("user_fname user_lname");
     * @return - {@link String} user full name.
     */
    public String getFullName() {
        return super.user.getUserDetail().getUserFname() + super.user.getUserDetail().getUserLname();
    }

}
