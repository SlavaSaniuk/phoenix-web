package com.phoenix.models;

import javax.jws.soap.SOAPBinding;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id", nullable = false)
    private int account_id;

    @Column(name = "account_email", nullable = false, unique = true)
    @NotNull(message = "Account email field must not be null.")
    @NotEmpty(message = "Account email field must not be empty")
    private String account_email;

    @Column(name = "account_password", nullable = false)
    @NotNull
    @NotEmpty
    private String account_password;

    @OneToOne(optional = false)
    @JoinColumn(name = "account_owner_user_id", nullable = false,
            unique = true, updatable = false)
    @MapsId
    private User account_owner;


    //Getters and setters
    public int getAccountId() {        return account_id;    }
    public void setAccountId(int account_id) {        this.account_id = account_id;    }

    public String getAccountEmail() {        return account_email;    }
    public void setAccountEmail(@NotNull @NotEmpty String account_email)  {
        this.account_email = account_email;
    }

    public String getAccountPassword() {        return account_password;    }
    public void setAccountPassword(@NotNull @NotEmpty  String account_password) {        this.account_password = account_password;    }

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
