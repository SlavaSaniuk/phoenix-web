package com.phoenix.services;

import com.phoenix.repositories.AccountRepository;
import com.phoenix.repositories.UserRepository;
import com.phoenix.services.accounting.AccountManagementService;
import com.phoenix.services.accounting.AccountManager;
import com.phoenix.services.accounting.SignAuthenticator;
import com.phoenix.services.accounting.SigningService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * {@link Configuration} application class. Class define all business login services beans.
 */
@Configuration
public class ServicesConfiguration {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(ServicesConfiguration.class);

    //Spring repositories
    private UserRepository user_repository;
    private AccountRepository account_repository;

    /**
     * Default constructor. Uses to logging about start of initialization services beans.
     */
    //Constructor
    public ServicesConfiguration() {
        LOGGER.info("Start to initialize " +getClass().getName() +" configuration class");
    }

    /**
     * {@link AccountManagementService} service bean. {@link AccountManager} implements this bean.
     * @return - {@link AccountManagementService} bean.
     */
    @Bean("AccountManager")
    public AccountManagementService accountManagementService() {
        return new AccountManager(this.account_repository);
    }

    /**
     * {@link SigningService} service bean. {@link SignAuthenticator} implements this bean.
     * @return - {@link SigningService} bean.
     */
    @Bean("SingingService")
    public SigningService signingService() {
        SignAuthenticator service = new SignAuthenticator(this.user_repository);
        service.setAccountManagementService(this.accountManagementService());
        return service;
    }

    //Spring autowiring
    @Autowired
    private void setUserRepository(UserRepository user_repository) {        this.user_repository = user_repository;    }
    @Autowired
    public void setAccountRepository(AccountRepository account_repository) {        this.account_repository = account_repository;    }
}
