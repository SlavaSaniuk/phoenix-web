package com.phoenix.unittestsdep.servicestest;


import com.phoenix.exceptions.EmailAlreadyRegisterException;
import com.phoenix.exceptions.JpaEngineException;
import com.phoenix.models.Account;
import com.phoenix.models.User;
import com.phoenix.repositories.UserRepository;
import com.phoenix.services.accounting.AccountManagementService;
import com.phoenix.services.accounting.SignAuthenticator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityExistsException;

@ExtendWith(MockitoExtension.class)
class SigningServiceRegistrationTestCase {

    @Mock UserRepository repository;
    @Mock AccountManagementService ams;

    @InjectMocks
    private SignAuthenticator authenticator;

    @BeforeEach
    void setUpBeforeEach() {
        this.authenticator.setAccountManagementService(this.ams);
    }

    @Test
    void signUp_repositoryCannotSaveNewUser_shouldThrowJpaEngineException() {
        Mockito.when(this.repository.save(new User())).thenReturn(new User());
        Assertions.assertThrows(JpaEngineException.class, ()-> this.authenticator.signUp(new Account()));
    }

    @Test
    void signUp_accountAlreadyRegistered_shouldThrowEmailAlreadyRegisteredException() {
        User created_user = new User();
        created_user.setUserId(23);
        Mockito.when(this.repository.save(new User())).thenReturn(created_user);

        Account account = new Account();
        account.setAccountId(23);
        Mockito.when(this.ams.registerAccount(account, created_user)).thenThrow(EntityExistsException.class);
        Assertions.assertThrows(EmailAlreadyRegisterException.class, ()-> this.authenticator.signUp(account));
    }

    @Test
    void signUp_accountAndUserIdAreNotSame_shouldThrowJpaEngineException() {
        User created_user = new User();
        created_user.setUserId(23);
        Mockito.when(this.repository.save(new User())).thenReturn(created_user);

        Account acc = new Account();
        Mockito.when(this.ams.registerAccount(acc, created_user)).thenReturn(24);

        Assertions.assertThrows(JpaEngineException.class, () -> this.authenticator.signUp(acc));

    }

    @Test
    void signUp_newAccount_shouldReturnGeneratedId() {
        User user = new User();
        int expected = 23;
        user.setUserId(expected);
        Mockito.when(this.repository.save(new User())).thenReturn(user);

        Account acc = new Account();
        Mockito.when(this.ams.registerAccount(acc, user)).thenReturn(expected);

        Assertions.assertEquals(expected, this.authenticator.signUp(acc));
    }



}
