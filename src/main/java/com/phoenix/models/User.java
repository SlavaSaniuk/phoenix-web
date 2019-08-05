package com.phoenix.models;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Hibernate user entity. This entity identifier user person by it's {@link User#user_id}.
 * When new user peron registered new account application automatically create new user entity
 * and associate account and users details with created user.
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

    @OneToMany(mappedBy = "post_owner", fetch = FetchType.EAGER)
    private List<Post> user_posts;

    //Getters
    public int getUserId() {        return user_id;    }
    public String getUserIdAlias() {        return user_id_alias;    }
    public Account getUserAccount() {        return user_account;    }
    public UserDetail getUserDetail() {        return user_detail;    }
    public List<Post> getUserPosts() {        return this.user_posts;    }

    //Setter
    public void setUserId(int user_id) {        this.user_id = user_id;    }
    public void setUserDetail(UserDetail user_detail) {        this.user_detail = user_detail;    }
    public void setUserIdAlias(String user_id_alias) {        this.user_id_alias = user_id_alias;    }
    public void setUserAccount(Account user_account) {        this.user_account = user_account;    }
    public void setUserPosts(List<Post> posts) {        this.user_posts = posts;    }

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

    @Override
    public String toString() {
        return "User: "
                    +"ID: " +user_id
                    +", Id_alias: " +user_id_alias;

    }
}
