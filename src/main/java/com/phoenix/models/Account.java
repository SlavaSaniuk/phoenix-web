package com.phoenix.models;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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


    //Getters and setters
    public int getAccountId() {        return account_id;    }
    public void setAccountId(int account_id) {        this.account_id = account_id;    }

    public String getAccountEmail() {        return account_email;    }
    public void setAccountEmail(@NotNull @NotEmpty String account_email)  {
        this.account_email = account_email;
    }

    public String getAccountPassword() {        return account_password;    }
    public void setAccountPassword(@NotNull @NotEmpty  String account_password) {        this.account_password = account_password;    }
}
