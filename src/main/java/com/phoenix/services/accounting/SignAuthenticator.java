package com.phoenix.services.accounting;

import com.phoenix.exceptions.EmailAlreadyRegisterException;
import com.phoenix.exceptions.JpaEngineException;
import com.phoenix.models.Account;
import com.phoenix.models.User;
import com.phoenix.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SignAuthenticator implements SigningService {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(SignAuthenticator.class);
    //SpringBeans
    private UserRepository repository; //Autowired in constructor
    private AccountManagementService ams; //Set in conf. class via setter method

    //Constructor
    public SignAuthenticator(UserRepository a_repository) {
        LOGGER.info("Create a " +SigningService.class.getName() +" bean implementation.");
        this.repository = a_repository;
        LOGGER.info(getClass().getName() +" was created as Spring bean.");
    }


    @Override
    public boolean signIn(Account account) {
        return false;
    }

    @Override
    @Transactional(rollbackFor = EmailAlreadyRegisterException.class)
    public int signUp(Account account) throws EmailAlreadyRegisterException, JpaEngineException {

        if (this.ams.isRegistered(account)) throw new EmailAlreadyRegisterException(account.getAccountEmail());

        User created_user = this.repository.save(new User());
        int generated_id = created_user.getUserId();
        if (generated_id == 0) throw new JpaEngineException("JPA can't to save new user entity");

        int account_id = this.ams.registerAccount(account, created_user);

        if (generated_id != account_id) throw new JpaEngineException("User ID is not same as associated account.");

        return generated_id;
    }

    /**
     * Set {@link AccountManagementService} service bean.
     * @param service - {@link AccountManagementService} bean implementation.
     */
    public void setAccountManagementService(AccountManagementService service) {
        LOGGER.debug("Set " +AccountManagementService.class.getName() +" to " +SigningService.class.getName() +" bean.");
        this.ams = service;
    }
}
