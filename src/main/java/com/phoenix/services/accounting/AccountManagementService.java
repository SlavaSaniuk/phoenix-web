package com.phoenix.services.accounting;

import com.phoenix.models.Account;
import com.phoenix.models.User;

import javax.persistence.EntityExistsException;

public interface AccountManagementService {


    int registerAccount(Account a_account, User a_user) throws EntityExistsException;

    /**
     *  Method perform some validation logic for created account before it is will be persisted in database {@link AccountManagementService#registerAccount(Account, User)}.
     * Method validate user password with properties defined in configuration file. Generate password hash and password salt. Reset password field.
     * @param a_account - {@link Account} which will be registered.
     * @return - Valid {@link Account} entity.
     */
    Account prepareAccount(Account a_account);
}
