package com.phoenix.services.accounting;

import com.phoenix.exceptions.EmailAlreadyRegisterException;
import com.phoenix.exceptions.JpaEngineException;
import com.phoenix.models.relation.users.Account;
import com.phoenix.models.relation.users.User;
import com.phoenix.models.forms.RegistrationForm;
import com.phoenix.models.relation.users.UserDetail;

/**
 *  Signing service service bean. Common purposes of this service
 *  authenticate user in system, or register his if he has not already
 *  registered. Default implementation of this service bean is
 *  {@link SignAuthenticator} class.
 */
public interface SigningService{


    User signIn(Account account) throws JpaEngineException, IllegalArgumentException;

    /**
     * Register user account in application system. Check whether entered
     * email already register. If not register validate user password with
     * constraints defined in configuration file. If account passwords valid reset
     * current account password, generate hash and salt, persist account
     * in database. Also persist created {@link UserDetail} entity.
     * @param form - {@link RegistrationForm} for registration.
     * @return - Generated user/account ID.
     * @throws EmailAlreadyRegisterException - throws if account email founded in database.
     * @throws JpaEngineException - throws if user / account ID is not the same.
     */
    User signUp(RegistrationForm form) throws EmailAlreadyRegisterException, JpaEngineException;
}
