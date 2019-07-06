package com.phoenix.unittests.servicestest;

import com.phoenix.services.security.hashing.HashAlgorithms;
import com.phoenix.services.security.hashing.PasswordHasher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

@Deprecated
class PasswordHasherTestCase {

    private static final PasswordHasher hasher = new PasswordHasher();

    @BeforeAll
    static void setUpBeforeAll() {
        hasher.setHashAlgorithm(HashAlgorithms.SHA_1);
    }

    @Test
    void hash_saltIsNull_shouldAutomaticallyGenerateSalt() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> hasher.hash("test", null));
    }

    @Test
    void hash_wordIsNull_shouldThrowIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> hasher.hash(null, "asdasd8s7d8asd8sd"));
    }

    @Test
    void hash_setWordAndSalt_shouldReturnFixedLengthHash() {
        String hash = hasher.hash("test", "asdsaddas");
        Assertions.assertEquals(40, hash.length());
    }

    @Test
    void hash_setWordAndSalt_shouldReturnNotNullHash() {
        String hash = hasher.hash("test", "asdsaddas");
        Assertions.assertNotNull(hash);
    }

    @Test
    void hash_setWordAndSalt_shouldReturnNotEmptyHash() {
        String hash = hasher.hash("test", "asdsaddas");
        Assertions.assertFalse(hash.isEmpty());
    }

    @Test
    void hash_setOnlyWord_shouldReturnGeneratedHash() {
        Assertions.assertFalse(hasher.hash("test").isEmpty());
    }

}
