package com.phoenix.models.forms;

import com.phoenix.models.Account;
import com.phoenix.webmvc.validation.annotations.Password;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * Login form object represent a HTML login form. Used by
 * Thymeleaf framework to bind users inputs to this form fields and validate this fields.
 * Hibernate entities can be created with {@link LoginForm#createAccount()} method.
 */
public class LoginForm {

    //Registration form inputs
    @NotEmpty(message = "{not_empty.Account.email}")
    @Email(message = "{invalid.Account.email}")
    private String email;

    @Password(message = "{invalid.Account.password}")
    private String password;

    /**
     * Create new Account entity and set it's email and password fields with current values.
     * @return - new {@link Account} entity.
     */
    public Account createAccount() {

        Account account = new Account();

        account.setAccountEmail(this.email);
        account.setAccountPassword(this.password);

        return account;
    }

    //Getters
    public String getEmail() {        return email;    }
    public String getPassword() {        return password;    }

    //Setters
    public void setEmail(String email) {        this.email = email;    }
    public void setPassword(String password) {        this.password = password;    }
}
