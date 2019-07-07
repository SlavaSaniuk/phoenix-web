package com.phoenix.integrationtests.servicestests;

import com.phoenix.integrationtests.configurationfortests.PersistenceTestConfiguration;
import com.phoenix.integrationtests.configurationfortests.RepositoriesTestConfiguration;
import com.phoenix.integrationtests.configurationfortests.ServicesTestConfiguration;
import com.phoenix.models.Account;
import com.phoenix.models.User;
import com.phoenix.repositories.UserRepository;
import com.phoenix.services.accounting.AccountManagementService;
import javax.persistence.EntityExistsException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@SpringJUnitConfig(classes = {
        PersistenceTestConfiguration.class, RepositoriesTestConfiguration.class, ServicesTestConfiguration.class
})
@ActiveProfiles("TEST")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AccountManagementServiceTestCase {

    private AccountManagementService ams; //Autowired
    private UserRepository repository; //Only for test methods

    /*
        Test for
            AccountManagementService#registerAccount(Account account);
     */

    @Test
    @Order(1)
    @Transactional()
    @Commit
    void registerAccount_newAccount_shouldReturnGeneratedId() {
        User user = new User();
        user = this.repository.save(user);

        Account account = new Account();
        account.setAccountEmail("test1@test.com");
        account.setAccountPassword("12345678");

        Assertions.assertNotEquals(0, this.ams.registerAccount(account, user));
    }

    @Test
    @Order(2)
    @Transactional()
    @Commit
    void registerAccount_newAccount_shouldWasCalledPrepareAccountMethod() {
        User user = new User();
        user = this.repository.save(user);

        Account account = new Account();
        account.setAccountEmail("test2@test.com");
        account.setAccountPassword("12345678");

        this.ams.registerAccount(account, user);

        Assertions.assertFalse(account.getAccountPasswordHash().isEmpty());
    }


    @Test
    @Order(3)
    @Transactional(rollbackFor = EntityExistsException.class)
    @Commit
    void registerAccount_accountAlreadyExist_shouldThrowEntityExistException() {
        User user = new User();
        user = this.repository.save(user);

        Account account = new Account();
        account.setAccountEmail("test1@test.com");
        account.setAccountPassword("12345678");

        User finalUser = user;
        Assertions.assertThrows(EntityExistsException.class, () -> {
            this.ams.registerAccount(account, finalUser);
        });
    }


    /*
        Test for
            AccountManagementService#prepareAccount(Account account);
     */
    @Test
    void prepareAccount_setAccount_shouldGeneratePasswordSalt() {
        Account account = new Account();
        account.setAccountPassword("12345678");
        Assertions.assertFalse(this.ams.prepareAccount(account).getAccountPasswordSalt().isEmpty());
    }

    @Test
    void prepareAccount_setAccount_shouldGeneratePasswordHash() {
        Account account = new Account();
        account.setAccountPassword("12345678");
        Assertions.assertFalse(this.ams.prepareAccount(account).getAccountPasswordHash().isEmpty());
    }

    @Test
    void prepareAccount_setAccount_shouldResetAccountPassword() {
        Account account = new Account();
        account.setAccountPassword("12345678");
        Assertions.assertNull(this.ams.prepareAccount(account).getAccountPassword());
    }

    //Spring autowiring
    @Autowired
    void setAms(AccountManagementService ams) {
        this.ams = ams;
    }

    @Autowired
    void setRepository(UserRepository repository) {
        this.repository = repository;
    }
}
