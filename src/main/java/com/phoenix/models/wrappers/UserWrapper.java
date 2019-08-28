package com.phoenix.models.wrappers;

import com.phoenix.models.relation.users.User;
import org.springframework.lang.NonNull;

public class UserWrapper {

    //Class field
    private User user;

    //Constructor
    public UserWrapper(@NonNull User user) {
        this.user = user;
    }

    public String getFullName() {
        return this.user.getUserDetail().getUserFname() +" "
                +this.user.getUserDetail().getUserLname();
    }

    //Getters
    public User getUser() {        return user;    }

    //Setters
    public void setUser(@NonNull User user) {        this.user = user;    }
}
