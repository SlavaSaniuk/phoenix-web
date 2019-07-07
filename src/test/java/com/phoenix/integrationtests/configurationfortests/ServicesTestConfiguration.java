package com.phoenix.integrationtests.configurationfortests;

import com.phoenix.repositories.AccountRepository;
import com.phoenix.repositories.UserRepository;
import com.phoenix.services.accounting.AccountManagementService;
import com.phoenix.services.accounting.AccountManager;
import com.phoenix.services.security.hashing.HashAlgorithms;
import com.phoenix.services.security.hashing.Hasher;
import com.phoenix.services.security.hashing.HashingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@Configuration
public class ServicesTestConfiguration {

    //Spring beans
    private UserRepository user_repository; //Autowired
    private AccountRepository account_repository; //Autowired

    @Bean
    public AccountManagementService accountManagementService() {
        AccountManager ams = new AccountManager(this.account_repository);
        ams.setHashingService(this.hashingService());
        return ams;
    }

    @Bean
    public HashingService hashingService() {
        Hasher hasher = new Hasher();
        hasher.setHashAlgorithm(HashAlgorithms.SHA_512);
        return hasher;
    }

    @Autowired
    public void setUserRepository(UserRepository user_repository) {
        this.user_repository = user_repository;
    }

    @Autowired
    public void setAccountRepository(AccountRepository account_repository) {
        this.account_repository = account_repository;
    }
}
