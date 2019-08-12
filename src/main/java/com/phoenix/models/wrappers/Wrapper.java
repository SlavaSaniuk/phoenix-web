package com.phoenix.models.wrappers;

import com.phoenix.models.User;

public class Wrapper {

    protected User user;

    //Constructors
    public Wrapper() {}

    public Wrapper(User a_user) {
        this.user = a_user;
    }

    //Getters
    public User getUser() {
        return user;
    }

    //Setters
    public void setUser(User user) {
        this.user = user;
    }

}
