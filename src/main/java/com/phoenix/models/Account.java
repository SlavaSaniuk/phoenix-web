package com.phoenix.models;

import com.phoenix.models.forms.RegistrationForm;
import java.util.Objects;
import javax.persistence.*;

/**
 * Account entity. Database holds all application account in relation form in 'account' table.
 * When new account registered in application, application automatically create new {@link User} entity
 * and generate it its unique ID, which is the same as {@link Account#account_id}.
 */
@SuppressWarnings("unused")
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
    private String account_email;

    private transient String account_password;

    @Column(name = "account_password_hash", nullable = false)
    private String account_password_hash;

    @Column(name = "account_password_salt", nullable = false)
    private String account_password_salt;

    //Constructors
    public Account() {}

    public Account(RegistrationForm form) {

        this.account_email = form.getEmail();
        this.account_password = form.getPassword();

    }

    //Getters and setters
    public int getAccountId() {        return account_id;    }

    public User getAccountOwner() {        return account_owner;    }
    public void setAccountOwner(User account_owner) {        this.account_owner = account_owner;    }

    public String getAccountEmail() {        return account_email;    }
    public void setAccountEmail(String account_email)  {
        this.account_email = account_email;
    }

    public String getAccountPassword() {        return account_password;    }
    public void setAccountPassword(String account_password) {        this.account_password = account_password;    }

    public String getAccountPasswordHash() {        return account_password_hash;    }
    public void setAccountPasswordHash(String account_password_hash) {        this.account_password_hash = account_password_hash;    }

    public String getAccountPasswordSalt() {        return account_password_salt;    }
    public void setAccountPasswordSalt(String account_password_salt) {        this.account_password_salt = account_password_salt;    }

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
