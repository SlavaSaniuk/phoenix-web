package com.phoenix.exceptions;

import com.phoenix.models.relation.users.Account;

import javax.persistence.EntityExistsException;

/**
 * {@link Exception} that throws in {@link com.phoenix.services.accounting.SigningService#signUp(Account)} method,
 * in case which {@link Account#getAccountEmail()} already registered in application. This exception extends {@link EntityExistsException}.
 */
public class EmailAlreadyRegisterException extends EntityExistsException {

    /**
     * Construct new exception with message like
     * "Email address '" +email +"' already register in system"
     * @param email - account email.
     */
    public EmailAlreadyRegisterException(String email) {
        super("Email address '" +email +"' already register in system");
    }



}
