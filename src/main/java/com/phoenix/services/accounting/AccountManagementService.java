package com.phoenix.services.accounting;

import com.phoenix.models.Account;
import com.phoenix.models.User;

import javax.persistence.EntityNotFoundException;

/**
 * Main service {@link org.springframework.context.annotation.Bean} for maintaining account system of whole application.
 * Contains method for registration, generating password hash, validation {@link Account} entities. Default
 * implementation of this interface is {@link AccountManager} class.
 */
public interface AccountManagementService {

    /**
     *  Method register account in application. Method associate this account with newly created {@link User}
     * entity because one user must to have only one account for authentication. Next method persist account
     * in database.
     * Note: Before register account check whether this account already
     * register {@link AccountManagementService#isRegistered(Account)}.
     * @param a_account - {@link Account} for registration.
     * @param a_user - {@link User} account owner user entity.
     * @return - Generated User/Account ID.
     */
    int registerAccount(Account a_account, User a_user) throws EntityNotFoundException;

    /**
     *  Method check if account already registered. Method found account row with the same
     * email {@link com.phoenix.repositories.AccountRepository#findAccountByAccountEmail(String)} in database.
     * @param a_account - verifiable {@link Account};
     * @return - {@code true}  - if account already register. In other wise - {@code false}.
     */
    boolean isRegistered(Account a_account);

    /**
     * Authenticate account by email and passwords field. Method find account by email address in database
     * . In case, if account not founded method return false. If account founded method generate account password
     * hash with salt of founded account, reset password field and compare two password hashes.
     * @param a_account - {@link Account} entity to authenticate.
     * @return -  "true" - if account password is same as founded account password, in otherwise - "false".
     */
    boolean authenticateAccount(Account a_account);
}
