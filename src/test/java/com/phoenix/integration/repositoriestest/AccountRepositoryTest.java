package com.phoenix.integration.repositoriestest;

import com.phoenix.configuration.PersistenceConfiguration;
import com.phoenix.integration.TestDataSourceConfig;
import com.phoenix.models.Account;
import com.phoenix.repositories.AccountRepository;
import com.phoenix.repositories.RepositoriesConfiguration;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@SpringJUnitConfig(classes = {
        TestDataSourceConfig.class,
        PersistenceConfiguration.class,
        RepositoriesConfiguration.class
})
@ActiveProfiles("TEST")
class AccountRepositoryTest {

    @Resource
    private AccountRepository repository;

    @ParameterizedTest
    @ValueSource(strings = {"test@test.com","Jhony@mail.us", "Herbert@gmail.ru"})
    @Commit
    @Transactional
    void saveAccount_defaultAccount_shouldReturnAccountWithGeneratedId(String a_email) {
        Account test = new Account();
        test.setAccountEmail(a_email);
        test.setAccountPassword("12345678");

        Assertions.assertNotEquals(0, this.repository.save(test).getAccountId());
    }

    @Test
    void findAccountByAccountEmail_nullEmail_shouldThrowIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.repository.findAccountByAccountEmail(null));
    }

    @Test
    @Transactional
    void findAccountByAccountEmail_validEmail_shouldReturnFoundedAccount() {
        String expected = "find-me";

        Account input = new Account();
        input.setAccountEmail(expected);
        input.setAccountPassword("This is me");

        this.repository.save(input);

        Assertions.assertEquals(expected, this.repository.findAccountByAccountEmail(expected).getAccountEmail());
    }

    @Test
    void findAccountByEmail_notExistedEmail_shouldReturnNull() {
        Assertions.assertNull(this.repository.findAccountByAccountEmail("asdasdasdsad"));
    }
}
