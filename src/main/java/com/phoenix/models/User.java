package com.phoenix.models;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, updatable = false)
    private int user_id;

    @Column(name = "user_id_alias", unique = true, length = 30)
    private String user_id_alias;

    //Getters and Setters
    public int getUserId() {        return user_id;    }

    public String getUserIdAlias() {        return user_id_alias;    }
    public void setUserIdAlias(String user_id_alias) {        this.user_id_alias = user_id_alias;    }
}
