package com.phoenix.integration.servicestest;

import com.phoenix.configuration.PersistenceConfiguration;
import com.phoenix.exceptions.EmailAlreadyRegisterException;
import com.phoenix.integration.TestDataSourceConfig;
import com.phoenix.models.Account;
import com.phoenix.repositories.RepositoriesConfiguration;
import com.phoenix.services.ServicesConfiguration;
import com.phoenix.services.accounting.SigningService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@SpringJUnitConfig(classes = {
        TestDataSourceConfig.class, PersistenceConfiguration.class, RepositoriesConfiguration.class, ServicesConfiguration.class
})
@ActiveProfiles("TEST")
class SigningServiceRegistrationTest {

    @Resource
    SigningService service;

    @Test
    @Transactional
    @Commit
    void signUp_newAccount_shouldReturnGeneratedId() {
        Account test = new Account();
        test.setAccountEmail("hello");
        test.setAccountPassword("world");

        Assertions.assertNotEquals(0, this.service.signUp(test));
    }

    @Test
    void signUp_accountAlreadyRegister_shouldThrowEmailAlreadyExistException() {
        Account acc_for_save = new Account();
        acc_for_save.setAccountEmail("hello2");
        acc_for_save.setAccountPassword("world");
        this.service.signUp(acc_for_save);

        Account invalid = new Account();
        invalid.setAccountEmail("hello2");
        invalid.setAccountPassword("new world");
        Assertions.assertThrows(EmailAlreadyRegisterException.class, ()-> this.service.signUp(invalid));
    }
}
