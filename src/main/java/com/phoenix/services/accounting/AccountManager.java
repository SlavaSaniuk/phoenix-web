package com.phoenix.services.accounting;

import com.phoenix.models.Account;
import com.phoenix.models.User;
import com.phoenix.repositories.AccountRepository;
import com.phoenix.services.security.hashing.HashingService;
import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;


/**
 * Class implements {@link AccountManagementService} interface.
 * It's a default implementation for account maintaining system.
 */
@Service
public class AccountManager implements AccountManagementService, InitializingBean {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountManager.class);

    //Spring beans
    private AccountRepository repository;
    private HashingService hasher;

    /**
     * Create new {@link AccountManager} service bean..
     * @param a_repository - {@link AccountRepository} - basic CRUD account repository.
     */
    public AccountManager(AccountRepository a_repository) {
        LOGGER.debug("Start to create " +getClass().getName() +" service bean.");

        LOGGER.debug("Map " +a_repository.getClass().getName() +" to " +getClass().getName() +" class.");
        this.repository = a_repository;
    }

    @Override
    public int registerAccount(Account a_account, User a_user) throws EntityNotFoundException {

        if (a_user.getUserId() == 0) throw new EntityNotFoundException("User entity must before persisted");

        //Prepare account to persist
        a_account = this.preparePassword(a_account);

        a_account.setAccountOwner(a_user);
        a_user.setUserAccount(a_account);

        //Persist account
        return this.repository.save(a_account).getAccountId();
    }

    @Override
    public boolean authenticateAccount(Account a_account) {

        //Find account
        Account founded = this.repository.findAccountByAccountEmail(a_account.getAccountEmail());
        if (founded == null) return false;

        //Compare passwords
        if (this.comparePassword(a_account, founded)) {
            a_account.setAccountId(founded.getAccountId());
            return true;
        }else return false;
    }

    @Override
    public boolean isRegistered(Account a_account) {

        //Found account with same name
        Account founded = this.repository.findAccountByAccountEmail(a_account.getAccountEmail());
        return founded != null;
    }

    /**
     * Methods generate salt for password, reset current account password, and generate password hash for account..
     * @param a_account - {@link Account} with defined {@link Account#getAccountPassword()}.
     * @return - {@link Account} with generated password hash and salt.
     */
    public Account preparePassword(Account a_account) {

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

    /**
     * Compare account password hashes of two accounts. Hash clear account password
     * of given account. Reset clear password (set it to null). Hash password hash with founded
     * account password salt. Compare given and password hashes.
     * @param given - {@link Account} with clear password.
     * @param founded -{@link Account} with password hash and salt
     * @return - true, if password hashes of twa accounts are same, in otherwise - false.
     */
    public boolean comparePassword(Account given, Account founded) {
        //hash clear password
        String hash = this.hasher.hash(given.getAccountPassword());

        //reset clear password
        given.setAccountPassword(null);

        //hash password hash with salt
        hash = this.hasher.hash(hash + founded.getAccountPasswordSalt());

        //Equals?
        return hash.equals(founded.getAccountPasswordHash());
    }

    //Setters
    /**
     * Set {@link HashingService) service bean.
     * @param service - {@link HashingService} bean.
     */
    public void setHashingService(HashingService service) {
        LOGGER.debug("Set " +service.getClass().getName() +" to " +getClass().getName() +" class.");
        this.hasher = service;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //Check account repository
        if(this.repository == null) {
            LOGGER.error("Not initializing " +AccountRepository.class.getName() +" repository bean in " +getClass().getName() +" service bean");
            throw new Exception(new BeanDefinitionStoreException(AccountRepository.class.getName() +" is not set"));
        }
        //Check Hashing service
        if (this.hasher == null) {
            LOGGER.error("Not initializing " +HashingService.class.getName() +" repository bean in " +getClass().getName() +" service bean");
            throw new Exception(new BeanDefinitionStoreException(HashingService.class.getName() +" is not set"));
        }
    }
}
