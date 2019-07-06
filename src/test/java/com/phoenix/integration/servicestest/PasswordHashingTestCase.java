package com.phoenix.integration.servicestest;

import com.phoenix.services.security.hashing.HashingService;
import com.phoenix.services.security.hashing.PasswordHasher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = ConfigurationForServicesTests.class)
class PasswordHashingTestCase {

    @Autowired
    @Qualifier("withAlg")
    HashingService service;

    @Test
    @Disabled
    void newHashingService_notSetHashAlgorithm_shouldUseDefaultSha_1() {
        PasswordHasher hasher = (PasswordHasher) this.service;
        Assertions.assertEquals("SHA-1", hasher.getHashAlgorithm());
    }

    @Test
    void newHashingService_setHashAlgorithm_shouldUseAlg() {
        PasswordHasher hasher = (PasswordHasher) this.service;
        Assertions.assertEquals("SHA-512", hasher.getHashAlgorithm());
    }

}
