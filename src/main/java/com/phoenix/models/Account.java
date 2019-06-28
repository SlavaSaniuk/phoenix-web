package com.phoenix.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "account", schema="phoenix")
public class Account {

    @Id
    @Column(name = "account_id", nullable = false)
    @NotNull
    private int account_id;

    @Column(name = "account_email", nullable = false, unique = true)
    @NotNull(message = "Account email field must not be null.")
    @NotEmpty(message = "Account email field must not be empty")
    private String account_email;

    @Column(name = "account_password", nullable = false)
    @NotNull
    @NotEmpty
    private String account_password;


    //Getters and setters
    public int getAccountId() {        return account_id;    }

    public String getAccountEmail() {        return account_email;    }
    public void setAccountEmail(@Valid String account_email)  {
        this.account_email = account_email;
    }

    public String getAccountPassword() {        return account_password;    }
    public void setAccountPassword(@Valid String account_password) {        this.account_password = account_password;    }
}
