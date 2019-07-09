package com.phoenix.unittestsdep.servicestest;

import com.phoenix.services.security.hashing.HashAlgorithms;
import com.phoenix.services.security.hashing.Hasher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

class HashServiceTestCase {

    @Order(1)
    @Test
    void hash_manuallySetAlgorithmMD5_shouldReturnGeneratedHash() {
        Hasher hasher = new Hasher();
        hasher.setHashAlgorithm(HashAlgorithms.MD5);
        String word = "Hello";
        String hash = hasher.hash(word);
        System.out.println(hash);
        Assertions.assertNotNull(hash);
        Assertions.assertFalse(hash.isEmpty());
        Assertions.assertEquals(32, hash.length());
    }

    @Order(2)
    @Test
    void hash_manuallySetAlgorithmSHA1_shouldReturnGeneratedHash() {
        Hasher hasher = new Hasher();
        hasher.setHashAlgorithm(HashAlgorithms.SHA_1);
        String word = "Hello";
        String hash = hasher.hash(word);
        System.out.println(hash);
        Assertions.assertNotNull(hash);
        Assertions.assertFalse(hash.isEmpty());
        Assertions.assertEquals(40, hash.length());
    }

    @Order(3)
    @Test
    void hash_manuallySetAlgorithmSHA256_shouldReturnGeneratedHash() {
        Hasher hasher = new Hasher();
        hasher.setHashAlgorithm(HashAlgorithms.SHA_256);
        String word = "Hello";
        String hash = hasher.hash(word);
        System.out.println(hash);
        Assertions.assertNotNull(hash);
        Assertions.assertFalse(hash.isEmpty());
        Assertions.assertEquals(64, hash.length());
    }

    @Order(4)
    @Test
    void hash_manuallySetAlgorithmSHA384_shouldReturnGeneratedHash() {
        Hasher hasher = new Hasher();
        hasher.setHashAlgorithm(HashAlgorithms.SHA_384);
        String word = "Hello";
        String hash = hasher.hash(word);
        System.out.println(hash);
        Assertions.assertNotNull(hash);
        Assertions.assertFalse(hash.isEmpty());
        Assertions.assertEquals(96, hash.length());
    }

    @Order(5)
    @Test
    void hash_manuallySetAlgorithmSHA512_shouldReturnGeneratedHash() {
        Hasher hasher = new Hasher();
        hasher.setHashAlgorithm(HashAlgorithms.SHA_512);
        String word = "Hello";
        String hash = hasher.hash(word);
        System.out.println(hash);
        Assertions.assertNotNull(hash);
        Assertions.assertFalse(hash.isEmpty());
        Assertions.assertEquals(128, hash.length());
    }

    @Order(6)
    @Test
    void generateSalt_manuallySetAlgorithmSHA256_shouldReturnGeneratedHash() {
        Hasher hasher = new Hasher();
        hasher.setHashAlgorithm(HashAlgorithms.SHA_256);

        String salt = hasher.generateSalt();
        System.out.println(salt);
        Assertions.assertNotNull(salt);
        Assertions.assertFalse(salt.isEmpty());
        Assertions.assertEquals(64, salt.length());
    }

    @Test
    void setHashAlgorithm_manuallySetHashAlgorithm_shouldUseAlgorithmSpecificValues() {

        Hasher hasher = new Hasher();
        hasher.setHashAlgorithm(HashAlgorithms.SHA_512);

        String hash = hasher.hash("This is a test");

        Assertions.assertEquals("SHA-512", hasher.getHashAlgorithm());
        Assertions.assertEquals(64, hasher.getSaltLength());
        Assertions.assertEquals(128, hash.length());

    }

}
