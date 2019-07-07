package com.phoenix.integrationdeprecated.servicestest;

import com.phoenix.services.security.hashing.HashAlgorithms;
import com.phoenix.services.security.hashing.HashingService;
import com.phoenix.services.security.hashing.PasswordHasher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ConfigurationForServicesTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationForServicesTests.class);

    @Bean("withoutAlg")
    public HashingService passwordHasherWithoutAlg() {
        LOGGER.info("Create " +HashingService.class.getName() +" service bean without specified hash algorithm.");
        return new PasswordHasher();
    }

    @Bean("withAlg")
    public HashingService passwordHasherWithAlg() {
        LOGGER.info("Create " +HashingService.class.getName() +" service bean with specified hash algorithm.");
        PasswordHasher hasher = new PasswordHasher();
        hasher.setHashAlgorithm(HashAlgorithms.SHA_512);
        return hasher;
    }
}
