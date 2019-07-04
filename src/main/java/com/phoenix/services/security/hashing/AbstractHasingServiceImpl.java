package com.phoenix.services.security.hashing;

public abstract class AbstractHasingServiceImpl implements HashingService{

    public String generateSalt(int salt_lenght) {
        byte[] bytes = new byte[salt_lenght];
        return "";
    }

}
