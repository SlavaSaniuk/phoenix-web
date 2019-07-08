package com.phoenix.services.security.hashing;

import com.phoenix.services.utilities.ConversionUtility;

import java.security.SecureRandom;

abstract class AbstractHashingServiceImpl implements HashingService{

    /**
     * length of hash string.
     */
    int hash_length;

    @Override
    public String generateSalt() {
        byte[] bytes = new byte[hash_length];
        SecureRandom rnd = new SecureRandom();
        rnd.nextBytes(bytes);
        return ConversionUtility.bytesToHex(bytes);
    }

}
