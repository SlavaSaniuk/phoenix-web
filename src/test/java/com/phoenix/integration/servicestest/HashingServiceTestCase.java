package com.phoenix.integration.servicestest;

import com.phoenix.services.security.hashing.Hasher;
import com.phoenix.services.security.hashing.HashingService;
import com.phoenix.services.security.hashing.HashingServiceFactoryBean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.annotation.Resource;

@SpringJUnitConfig(classes = {
    ServicesConfigurationForTests.class
})
class HashingServiceTestCase {

    @Resource
    HashingServiceFactoryBean test;

    @Test
    @Disabled
    void getObject_notSetHashAlgorithm_shouldUseDefaultSha512Algorithm() throws Exception {

        Hasher test_hasher = (Hasher) this.test.getObject();
        Assertions.assertEquals("SHA-512", test_hasher.getHashAlgorithm());
    }

    @Test
    void getObject_setSha256Algorithm_shouldUseSha256Algorithm() throws Exception {
        Hasher test_hasher = (Hasher) this.test.getObject();
        Assertions.assertEquals("SHA-256", test_hasher.getHashAlgorithm());
    }

    @Test
    void hash_hashWord_shouldReturnNotEmptyFixedLengthString() throws Exception {
        HashingService service = this.test.getObject();
        String hash = service.hash("Hello world!");
        Assertions.assertNotNull(hash);
        Assertions.assertEquals(64, hash.length());
        Assertions.assertFalse(hash.isEmpty());
    }
}
