package com.phoenix.services.accounting;

import com.phoenix.models.Account;
import com.phoenix.models.User;

public interface AccountManagementService {


    int registerAccount(Account a_account, User a_user);
}
