package com.phoenix.services.accounting;

import com.phoenix.exceptions.EmailAlreadyRegisterException;
import com.phoenix.exceptions.JpaEngineException;
import com.phoenix.models.Account;

/**
 *  Signing service service bean. Common purposes of this service
 *  authenticate user in system, or register his if he has not already
 *  registered. Default implementation of this service bean is
 *  {@link SignAuthenticator} class.
 */
public interface SigningService{

    /**
     * Authenticate user in application runtime. Check whether is this user
     * registered in application by founding it's account by email address.
     * Compare it's entered password with password hash.
     * @param account - Valid {@link Account} entity with defined email
     *                and password fields.
     * @return - 'true' - if user entered correct account email and
     * password fields. In other wise - 'false'.
     */
    boolean signIn(Account account);

    /**
     * Register user account in application system. Check whether entered
     * email already register. If not register validate user password with
     * constraints defined in configuration file. If account passwords valid reset
     * current account password, generate hash and salt, persist account
     * in database.
     * @param account - {@link Account} for registration.
     * @return - Generated user/account ID.
     * @throws EmailAlreadyRegisterException - throws if account email founded in database.
     * @throws JpaEngineException - throws if user / account ID is not the same.
     */
    int signUp(Account account) throws EmailAlreadyRegisterException, JpaEngineException;
}
