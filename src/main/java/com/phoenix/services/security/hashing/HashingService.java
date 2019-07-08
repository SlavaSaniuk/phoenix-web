package com.phoenix.services.security.hashing;

/**
 * Hashing service interface define common methods
 * which helps us to hash another word or password.
 */
public interface HashingService {

    /**
     * Common method to hash another {@link String}.
     * @param word - {@link String} to hash.
     * @return - Hash in hexadecimal format.
     */
    String hash(String word);

    /**
     * Method generate random sequence of hexadecimal chars.
     * Length of resulting string is fixed and set by hash algorithm (hash length = salt length)
     * @return - Fixed length hexadecimal string.
     */
    String generateSalt();
}
