package com.phoenix.models;

import javax.persistence.*;
import java.util.Objects;

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


    //Getters and Setters
    public int getUserId() {        return user_id;    }
    public void setUserId(int user_id) {        this.user_id = user_id;    }

    public String getUserIdAlias() {        return user_id_alias;    }
    public void setUserIdAlias(String user_id_alias) {        this.user_id_alias = user_id_alias;    }

    public Account getUserAccount() {        return user_account;    }
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
