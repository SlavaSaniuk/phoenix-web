package com.phoenix.integration.servicestest;

import com.phoenix.configuration.PersistenceConfiguration;
import com.phoenix.integration.TestDataSourceConfig;
import com.phoenix.models.Account;
import com.phoenix.models.User;
import com.phoenix.repositories.RepositoriesConfiguration;
import com.phoenix.services.ServicesConfiguration;
import com.phoenix.services.accounting.AccountManagementService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityExistsException;

@SpringJUnitConfig(classes = {
        TestDataSourceConfig.class,
        PersistenceConfiguration.class,
        RepositoriesConfiguration.class,
        ServicesConfiguration.class,
})
@ActiveProfiles("TEST")
class AccountManagementServiceTestCase {

    @Resource
    AccountManagementService ams;

    @Test
    @Transactional
    @Commit
    void registerAccount_newAccount_shouldReturnGeneratedId() {

        Account test = new Account();
        test.setAccountEmail("ams@test.com");
        test.setAccountPassword("amsamsasm");

        Assertions.assertNotEquals(0, this.ams.registerAccount(test, new User()));
    }

    @Test
    @Transactional
    @Commit
    void registerAccount_accountAlreadyRegistered_shouldThrowEntityAlreadyExist() {

        String same_email = "ams2@test.com";

        Account for_save = new Account();
        for_save.setAccountEmail(same_email);
        for_save.setAccountPassword("amsamsasm");

        this.ams.registerAccount(for_save, new User());

        Account invalid = new Account();
        invalid.setAccountEmail(same_email);
        invalid.setAccountPassword("Test_password");

        Assertions.assertThrows(EntityExistsException.class, ()-> this.ams.registerAccount(invalid, new User()));

    }

    @Test
    void prepareAccount_validAccount_shouldReturnAccountWithGeneratedPasswordHash() {
        Account test = new Account();
        test.setAccountPassword("12345678");
        test = this.ams.prepareAccount(test);
        System.out.println(test.getAccountPasswordHash());
        Assertions.assertFalse(test.getAccountPasswordHash().isEmpty());
    }

    @Test
    void prepareAccount_validAccount_shouldReturnAccountWithGeneratedPasswordSalt() {
        Account test = new Account();
        test.setAccountPassword("12345678");
        test = this.ams.prepareAccount(test);
        System.out.println(test.getAccountPasswordSalt());
        Assertions.assertFalse(test.getAccountPasswordSalt().isEmpty());
    }

    @Test
    void prepareAccount_validAccount_shouldResetAccountPassword() {
        Account test = new Account();
        test.setAccountPassword("12345678");
        test = this.ams.prepareAccount(test);
        Assertions.assertNull(test.getAccountPassword());
    }
}
