package com.phoenix.models;

import javax.persistence.*;
import java.util.Objects;

/**
 * User entity. Database holds all application account in relation form in 'user' table.
 * Every user must have only one {@link Account}, because JPA used {@link OneToOne} annotation on {@link User#user_account} account.
 * Method automatically create new user entity and generate unique ID to it {@link User#user_id}.
 */

@SuppressWarnings("unused")
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, updatable = false)
    private int user_id;

    @Column(name = "user_id_alias", unique = true, length = 30)
    private String user_id_alias;

    @OneToOne(mappedBy = "account_owner")
    private Account user_account;

    @OneToOne(mappedBy = "detail_owner")
    private UserDetail user_detail;


    //Getters and Setters
    public int getUserId() {        return user_id;    }
    public String getUserIdAlias() {        return user_id_alias;    }
    public Account getUserAccount() {        return user_account;    }
    public UserDetail getUserDetail() {        return user_detail;    }

    public void setUserId(int user_id) {        this.user_id = user_id;    }
    public void setUserDetail(UserDetail user_detail) {        this.user_detail = user_detail;    }
    public void setUserIdAlias(String user_id_alias) {        this.user_id_alias = user_id_alias;    }
    public void setUserAccount(Account user_account) {        this.user_account = user_account;    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return user_id == user.user_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id);
    }
}
