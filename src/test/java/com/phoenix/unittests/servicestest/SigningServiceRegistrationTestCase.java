package com.phoenix.unittests.servicestest;

import com.phoenix.exceptions.EmailAlreadyRegisterException;
import com.phoenix.exceptions.JpaEngineException;
import com.phoenix.models.Account;
import com.phoenix.models.User;
import com.phoenix.repositories.UserRepository;
import com.phoenix.services.accounting.AccountManagementService;
import com.phoenix.services.accounting.SignAuthenticator;
import javax.persistence.EntityExistsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SigningServiceRegistrationTestCase {

    @Mock UserRepository repository;
    @Mock AccountManagementService ams;

    @InjectMocks
    private SignAuthenticator authenticator;

    @Test
    void signUp_accountAlreadyExist_shouldThrowEmailAlreadyRegisteredException() {
        Mockito.when(ams.registerAccount(new Account(), new User())).thenThrow(EntityExistsException.class);
        Assertions.assertThrows(EmailAlreadyRegisterException.class, () -> this.authenticator.signUp(new Account()));
    }

    @Test
    void signUp_newAccount_shouldReturnGeneratedId() {
        int expected = 23;
        User output = new User();
        output.setUserId(expected);

        Mockito.when(this.repository.save(new User())).thenReturn(output);

        Account acc = new Account();
        acc.setAccountEmail("asdsa");
        acc.setAccountPassword("12345678");
        Mockito.lenient().when(this.ams.registerAccount(acc, output)).thenReturn(expected);

        Assertions.assertEquals(expected, this.authenticator.signUp(acc));
    }

    @Test
    void signUp_repositoryCannotSaveNewUser_shouldThrowJpaEngineException() {
        Mockito.when(this.repository.save(new User())).thenReturn(new User());
        Assertions.assertThrows(JpaEngineException.class, ()-> this.authenticator.signUp(new Account()));
    }

    @Test
    void signUp_userAccountIdIsNotSame_shouldThrowJpaEngineException() {
        User input = new User();
        input.setUserId(23);
        Mockito.when(this.repository.save(new User())).thenReturn(input);
        Mockito.when(this.ams.registerAccount(new Account(), new User())).thenReturn(24);

        Assertions.assertThrows(JpaEngineException.class, ()-> this.authenticator.signUp(new Account()));

    }



}
