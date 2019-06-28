package com.phoenix.unittests.entitytest;

import com.phoenix.models.Account;
import com.phoenix.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserEntityTest {

    @Test
    void newUser_defaultConstructor_shouldReturnDefaultUserId() {
        User user = new User();
        Assertions.assertEquals(0, user.getUserId());
    }

    @Test
    void newUser_setIdAlias_shouldReturnEstablishedAlias() {
        User user = new User();
        String alias = "test";
        user.setUserIdAlias(alias);
        Assertions.assertEquals(alias, user.getUserIdAlias());
    }

    @Test
    void setUserAccount_newAccount_ShouldBeEquals() {
        Account test = new Account();
        User test_u = new User();
        test_u.setUserAccount(test);
        Assertions.assertEquals(test, test_u.getUserAccount());
    }



}
