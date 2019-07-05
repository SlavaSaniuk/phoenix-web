package com.phoenix.services.security.hashing;

import com.phoenix.services.utility.ConversionUtility;

import java.security.SecureRandom;

abstract class AbstractHashingServiceImpl implements HashingService{

    protected int hash_length;

    public String generateSalt(int salt_length) {
        byte[] bytes = new byte[salt_length];
        SecureRandom rnd = new SecureRandom();
        rnd.nextBytes(bytes);
        return ConversionUtility.bytesToHex(bytes);
    }

}
