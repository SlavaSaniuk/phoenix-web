package com.phoenix.services.accounting;

import com.phoenix.exceptions.EmailAlreadyRegisterException;
import com.phoenix.exceptions.JpaEngineException;
import com.phoenix.models.Account;
import com.phoenix.models.User;
import com.phoenix.models.UserDetail;
import com.phoenix.models.forms.RegistrationForm;
import com.phoenix.repositories.UserRepository;
import com.phoenix.services.users.DetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SignAuthenticator implements SigningService, InitializingBean {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(SignAuthenticator.class);
    //SpringBeans
    private UserRepository repository; //Autowired in constructor
    private AccountManagementService ams; //Set in conf. class via setter method
    private DetailsService details; //Set in services configuration class via setter method

    /**
     * Construct new {@link SignAuthenticator} bean with already installed {@link UserRepository} repository bean.
     * @param a_repository - Implementation of {@link UserRepository} bean.
     */
    //Constructor
    public SignAuthenticator(UserRepository a_repository) {
        LOGGER.debug("Start to create " +getClass().getName() +" service bean.");

        this.repository = a_repository;
        LOGGER.debug("Map " +a_repository.getClass().getName() +" to " +getClass().getName() +" service bean");
    }


    @Override
    public User signIn(Account account) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = EmailAlreadyRegisterException.class)
    public int signUp(RegistrationForm form) throws EmailAlreadyRegisterException, JpaEngineException {

        //Create account and details entities
        Account account = form.createAccount();
        UserDetail detail = form.createUserDetail();

        //Check if account already registered
        if (this.ams.isRegistered(account)) throw new EmailAlreadyRegisterException(account.getAccountEmail());

        //Create new user
        User created_user = this.repository.save(new User());
        int generated_id = created_user.getUserId();
        LOGGER.debug("Created user id: " +generated_id);
        if (generated_id == 0) throw new JpaEngineException("JPA can't to save new user entity");

        //Register new account
        int account_id = this.ams.registerAccount(account, created_user);
        LOGGER.debug("Registered account ID: " +account_id);
        if (generated_id != account_id) throw new JpaEngineException("User ID is not same as associated account.");

        //Register user details
        int detail_id = this.details.registerNewDetail(detail, created_user);
        LOGGER.debug("Registered details ID: " +detail_id);
        if (generated_id != detail_id) throw new JpaEngineException("User ID is not same as associated user details.");

        return generated_id;
    }

    /**
     * Set {@link AccountManagementService} service bean.
     * @param service - {@link AccountManagementService} bean implementation.
     */
    public void setAccountManagementService(AccountManagementService service) {
        LOGGER.debug("Mapping: " +service.getClass().getName() +" to: " +getClass().getName());
        this.ams = service;
    }

    /**
     * Set {@link DetailsService} service bean.
     * @param details - {@link DetailsService} bean implementation.
     */
    public void setDetailsService(DetailsService details) {
        LOGGER.debug("Mapping: " +details.getClass().getName() +" to: " +getClass().getName());
        this.details = details;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        if(this.repository == null) {
            LOGGER.error("Not initializing " +UserRepository.class.getName() +" repository bean in " +getClass().getName() +" service bean");
            throw new Exception(new BeanDefinitionStoreException(UserRepository.class.getName() +"is not set."));
        }

        if (this.ams == null) {
            LOGGER.error("Not initializing " +AccountManagementService.class.getName() +" service bean in " +getClass().getName() +" service bean");
            throw new Exception(new BeanDefinitionStoreException(AccountManagementService.class.getName() +"is not set."));
         }


        if (this.details == null) {
            LOGGER.error("Not initializing " +DetailsService.class.getName() +" service bean in " +getClass().getName() +" service bean");
            throw new Exception(new BeanDefinitionStoreException(DetailsService.class.getName() +"is not set."));
        }
    }
}
