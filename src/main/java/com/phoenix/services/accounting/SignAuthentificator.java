package com.phoenix.services.accounting;

import com.phoenix.exceptions.EmailAlreadyRegisterException;

public class SignAuthentificator implements SigningService {

    @Override
    public boolean signIn() {
        return false;
    }

    @Override
    public int signUp() throws EmailAlreadyRegisterException {
        return 0;
    }
}
