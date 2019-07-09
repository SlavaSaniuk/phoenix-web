package com.phoenix.unittestsdep.servicestest;

import com.phoenix.models.Account;
import com.phoenix.models.User;
import com.phoenix.repositories.AccountRepository;
import com.phoenix.services.accounting.AccountManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityExistsException;

@ExtendWith(MockitoExtension.class)
class AccountManagementServiceTest {

    @Mock
    AccountRepository repository;

    @InjectMocks
    AccountManager manager;

    @Test
    @DisplayName("Should return generated user ID")
    void registerAccount_NewAccount_shouldReturnGeneratedId() {
        Account acc = new Account();

        int expected_id = 23;
        Account returned = new Account();
        returned.setAccountId(expected_id);

        Mockito.when(repository.save(acc)).thenReturn(returned);

        Assertions.assertEquals(expected_id, this.manager.registerAccount(acc, new User()));
    }

    @Test
    @DisplayName("Entity already exist exception")
    void registerAccount_accountAlreadyRegistered_shouldThrowEntityAlreadyExistException() {

        Account input = new Account();

        Mockito.when(repository.save(input)).thenThrow(EntityExistsException.class);

        Assertions.assertThrows(EntityExistsException.class, ()-> this.manager.registerAccount(input, new User()));
    }

    /*
        prepareAccount(Account a_account) method.
    */
    // Password hash generation
    @Test
    @DisplayName("Nullable password field")
    void prepareAccount_validPassword_shouldResetPasswordField() {

        Account account = new Account();
        account.setAccountPassword("It's a test");

        account = this.manager.prepareAccount(account);

        Assertions.assertNull(account.getAccountPassword());
    }

    @Test
    @DisplayName("Not empty password hash")
    void prepareAccount_validPassword_shouldReturnNotEmptyPasswordHashField() {

        Account account = new Account();
        account.setAccountPassword("It's a test");

        account = this.manager.prepareAccount(account);

        Assertions.assertFalse(account.getAccountPasswordHash().isEmpty());
    }

    @Test
    @DisplayName("Not empty password salt")
    void prepareAccount_validPassword_shouldReturnNotEmptyPasswordSaltField() {

        Account account = new Account();
        account.setAccountPassword("It's a test");

        account = this.manager.prepareAccount(account);

        Assertions.assertFalse(account.getAccountPasswordSalt().isEmpty());
    }
}
