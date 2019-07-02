package com.phoenix.models;

import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

/**
 * Account entity. Database holds all application account in relation form in 'account' table.
 * When new account registered in application, application automatically create new {@link User} entity
 * and generate it its unique ID, which is the same as {@link Account#account_id}.
 */
@Entity
@Table(name = "account")
public class Account {

    @Id
    private int account_id;

    @OneToOne(optional = false)
    @JoinColumn(name = "account_id", nullable = false,
            unique = true, updatable = false)
    @MapsId
    private User account_owner;

    @Column(name = "account_email", nullable = false, unique = true)
    @NotEmpty(message = "{notempty.Account.email}")
    private String account_email;

    @Column(name = "account_password", nullable = false)
    @NotEmpty(message = "{notempty.Account.password}")
    private String account_password;



    //Getters and setters
    public int getAccountId() {        return account_id;    }
    public void setAccountId(int account_id) {        this.account_id = account_id;    }

    public String getAccountEmail() {        return account_email;    }
    public void setAccountEmail(@NotEmpty String account_email)  {
        this.account_email = account_email;
    }

    public String getAccountPassword() {        return account_password;    }
    public void setAccountPassword(@NotEmpty  String account_password) {        this.account_password = account_password;    }

    public User getAccountOwner() {        return account_owner;    }
    public void setAccountOwner(User account_owner) {        this.account_owner = account_owner;    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return account_id == account.account_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(account_id);
    }
}
