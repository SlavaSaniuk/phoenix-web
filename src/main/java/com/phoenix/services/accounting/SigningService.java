package com.phoenix.services.accounting;

import com.phoenix.exceptions.EmailAlreadyRegisterException;
import com.phoenix.exceptions.JpaEngineException;
import com.phoenix.models.Account;

/**
 *
 */
public interface SigningService{

    boolean signIn(Account account);

    int signUp(Account account) throws EmailAlreadyRegisterException, JpaEngineException;
}
