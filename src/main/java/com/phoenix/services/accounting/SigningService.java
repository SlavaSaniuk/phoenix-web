package com.phoenix.services.accounting;

import com.phoenix.exceptions.EmailAlreadyRegisterException;

public interface SigningService{

    boolean signIn();

    int signUp() throws EmailAlreadyRegisterException;
}
