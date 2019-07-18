package com.phoenix.services;

import com.phoenix.repositories.AccountRepository;
import com.phoenix.repositories.UserDetailRepository;
import com.phoenix.repositories.UserRepository;
import com.phoenix.services.accounting.AccountManagementService;
import com.phoenix.services.accounting.AccountManager;
import com.phoenix.services.accounting.SignAuthenticator;
import com.phoenix.services.accounting.SigningService;
import com.phoenix.services.security.hashing.HashAlgorithms;
import com.phoenix.services.security.hashing.Hasher;
import com.phoenix.services.security.hashing.HashingService;

import com.phoenix.services.users.DetailsManager;
import com.phoenix.services.users.DetailsService;
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
    private UserDetailRepository detail_repository;

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
        LOGGER.info("Create " +AccountManagementService.class.getName() +" service bean.");

        LOGGER.debug(AccountManagementService.class.getName() +" is implemented by " +AccountManager.class.getName() +" class.");
        LOGGER.debug(AccountManager.class.getName() +": Set " +AccountRepository.class.getName() +" repository bean.");
        AccountManager manager = new AccountManager(this.account_repository);

        LOGGER.debug(AccountManager.class.getName() +": Set " +HashingService.class.getName() +" service bean.");
        manager.setHashingService(this.passwordHashingService());

        LOGGER.debug(AccountManagementService.class.getName() +" was created.");
        return manager;
    }

    /**
     * {@link SigningService} service bean. {@link SignAuthenticator} implements this bean.
     * @return - {@link SigningService} bean.
     */
    @Bean("SingingService")
    public SigningService signingService() {
        LOGGER.info("Create " +SigningService.class.getName() +" service bean.");

        LOGGER.debug(SigningService.class.getName() +" is implemented by " +SignAuthenticator.class.getName() +" class.");
        LOGGER.debug(SignAuthenticator.class.getName() +": set " +UserRepository.class.getName() +" repository bean.");
        SignAuthenticator service = new SignAuthenticator(this.user_repository);

        LOGGER.debug(SignAuthenticator.class.getName() +": set " +AccountManagementService.class.getName() +"service bean.");
        service.setAccountManagementService(this.accountManagementService());

        LOGGER.debug(SignAuthenticator.class.getName() +": set " +DetailsService.class.getName() +"service bean.");
        service.setDetailsService(this.detailsService());

        LOGGER.debug(SigningService.class.getName() +" was created.");
        return service;
    }

    /**
     * {@link HashingService} service bean. {@link Hasher} implements this bean.
     * @return - {@link HashingService} bean.
     */
    @Bean("PasswordHasher")
    public HashingService passwordHashingService() {
        LOGGER.info("Create " +HashingService.class.getName() +" service bean.");

        LOGGER.debug(HashingService.class.getName() +" is implemented by " +Hasher.class.getName() +" class.");
        Hasher hasher = new Hasher();

        LOGGER.debug(Hasher.class.getName() +": Set SHA-512 hash algorithm.");
        hasher.setHashAlgorithm(HashAlgorithms.SHA_512);

        LOGGER.debug(HashingService.class.getName() +" was created.");
        return hasher;
    }

    @Bean("UserDetailsService")
    public DetailsService detailsService() {

        LOGGER.info("Create " +DetailsService.class.getName() +" service bean.");

        DetailsManager manager = new DetailsManager(this.detail_repository);
        LOGGER.debug(DetailsService.class.getName() +" is implemented by " +manager.getClass().getName() +" service bean");

        return manager;
    }

    //Spring autowiring
    @Autowired
    private void setUserRepository(UserRepository user_repository) {
        LOGGER.debug("AUTOWIRING: " +UserRepository.class.getName() +" in " +getClass().getName());
        this.user_repository = user_repository;    }
    @Autowired
    public void setAccountRepository(AccountRepository account_repository) {
        LOGGER.debug("AUTOWIRING: " + AccountRepository.class.getName() + " in " + getClass().getName());
        this.account_repository = account_repository;
    }
    @Autowired
    public void setDetailRepository(UserDetailRepository detail_repository) {
        LOGGER.debug("Autowire: " +detail_repository.getClass().getName() +" in " +getClass().getName());
        this.detail_repository = detail_repository;
    }
}
