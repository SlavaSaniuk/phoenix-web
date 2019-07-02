package com.phoenix.services.accounting;

import com.phoenix.models.Account;
import com.phoenix.models.User;
import com.phoenix.repositories.AccountRepository;
import javax.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountManager implements AccountManagementService {

    //Beans
    private AccountRepository repository;


    @Autowired
    public AccountManager(AccountRepository a_repository) {
            this.repository = a_repository;
    }

    @Override
    public int registerAccount(Account a_account, User a_user) throws EntityExistsException{

        //Found account with same name
        Account founded = this.repository.findAccountByAccountEmail(a_account.getAccountEmail());
        if (founded != null) throw new EntityExistsException("Account already registered.");

        a_account.setAccountOwner(a_user);
        a_user.setUserAccount(a_account);

        //Persist account
        return this.repository.save(a_account).getAccountId();
    }
}
