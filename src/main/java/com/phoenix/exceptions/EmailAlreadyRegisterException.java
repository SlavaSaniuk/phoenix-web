package com.phoenix.exceptions;

import javax.persistence.EntityExistsException;

public class EmailAlreadyRegisterException extends EntityExistsException {

    public EmailAlreadyRegisterException() {
        super();
    }

    public EmailAlreadyRegisterException(String email) {
        super("Email address '" +email +"' already register in system");
    }



}
