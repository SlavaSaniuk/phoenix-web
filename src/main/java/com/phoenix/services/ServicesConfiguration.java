package com.phoenix.services;

import com.phoenix.repositories.AccountRepository;
import com.phoenix.services.accounting.AccountManagementService;
import com.phoenix.services.accounting.AccountManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServicesConfiguration {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(ServicesConfiguration.class);

    public ServicesConfiguration() {
        LOGGER.info("Start to initialize " +getClass().getName() +" configuration class");
    }

    @Bean("AccountManager")
    public AccountManagementService accountManagementService(@Autowired AccountRepository repository) {
        return new AccountManager(repository);
    }
}
