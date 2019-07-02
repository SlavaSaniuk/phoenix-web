package com.phoenix.services.accounting;

import com.phoenix.models.Account;
import com.phoenix.models.User;

import javax.persistence.EntityExistsException;

public interface AccountManagementService {


    int registerAccount(Account a_account, User a_user) throws EntityExistsException;
}
