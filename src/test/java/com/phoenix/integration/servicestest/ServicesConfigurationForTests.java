package com.phoenix.integration.servicestest;

import com.phoenix.services.security.hashing.HashAlgorithms;
import com.phoenix.services.security.hashing.HashingServiceFactoryBean;
import org.springframework.context.annotation.Bean;


public class ServicesConfigurationForTests {


    @Bean
    public HashingServiceFactoryBean hashingServiceFactory2() {
        HashingServiceFactoryBean factory = new HashingServiceFactoryBean();
        factory.setHashAlgorithm(HashAlgorithms.SHA_256);
        return factory;
    }

}
