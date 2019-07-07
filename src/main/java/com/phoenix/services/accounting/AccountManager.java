package com.phoenix.services.accounting;

import com.phoenix.models.Account;
import com.phoenix.models.User;
import com.phoenix.repositories.AccountRepository;
import com.phoenix.services.security.hashing.HashingService;
import javax.persistence.EntityExistsException;
import org.springframework.stereotype.Service;

/**
 * Class implements {@link AccountManagementService} interface.
 * It's a default implementation for account maintaining system.
 */
@Service
public class AccountManager implements AccountManagementService {

    //Spring beans
    private AccountRepository repository;
    private HashingService hasher;

    /**
     * Create new {@link AccountManager} object.
     * @param a_repository - {@link AccountRepository} - basic CRUD account repository.
     */
    public AccountManager(AccountRepository a_repository) {
            this.repository = a_repository;
    }

    @Override
    public int registerAccount(Account a_account, User a_user) throws EntityExistsException{

        //Prepare account to persist
        a_account = this.prepareAccount(a_account);

        a_account.setAccountOwner(a_user);
        a_user.setUserAccount(a_account);

        //Persist account
        return this.repository.save(a_account).getAccountId();
    }

    @Override
    public boolean isRegistered(Account a_account) {

        //Found account with same name
        Account founded = this.repository.findAccountByAccountEmail(a_account.getAccountEmail());
        return founded != null;
    }

    @Override
    public Account prepareAccount(Account a_account) {

        //Generate account password hash and salt
        String password_hash = this.hasher.hash(a_account.getAccountPassword());
        String password_salt = this.hasher.generateSalt();

        //Reset account password
        a_account.setAccountPassword(null);

        //Set generated hashes to entity
        a_account.setAccountPasswordHash(this.hasher.hash(password_hash + password_salt));
        a_account.setAccountPasswordSalt(password_salt);

        return a_account;
    }

    //Getters and Setters
    /**
     * Set {@link HashingService) service bean.
     * @param service - {@link HashingService} bean.
     */
    public void setHashingService(HashingService service) {
        this.hasher = service;
    }
}
