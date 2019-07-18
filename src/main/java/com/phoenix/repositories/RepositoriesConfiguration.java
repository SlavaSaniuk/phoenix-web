package com.phoenix.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.repository.query.QueryLookupStrategy;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * {@link RepositoriesConfiguration} configuration class contains CRUD, JPA repositories beans of whole application.
 * Spring automatically create a repositories implementations.
 */
@Configuration
public class RepositoriesConfiguration {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoriesConfiguration.class);

    @PersistenceContext
    EntityManager em; //Autowired

    //Spring beans
    //Factory that's create a repository implementation
    private JpaRepositoryFactory factory;

    /**
     * Default constructor. Inform about loading of this configuration class.
     */
    public RepositoriesConfiguration() {
        LOGGER.info("Start to initialize " +getClass().getName() +" configuration class");
    }

    /**
     * {@link UserRepository} bean. Spring automatically create this interface
     * implementation and return it as spring bean.
     * @return - {@link UserRepository} repository bean.
     */
    @Bean("UserRepository")
    public UserRepository userRepository() {
        LOGGER.debug("Create " +UserRepository.class.getName() +" repository bean.");
        return factory.getRepository(UserRepository.class);
    }

    /**
     * {@link AccountRepository} bean. Spring automatically create this interface
     *      * implementation and return it as spring bean
     * @return {@link AccountRepository} repository bean.
     */
    @Bean("AccountRepository")
    public AccountRepository accountRepository() {
        LOGGER.debug("Create " +AccountRepository.class.getName() +" repository bean.");
        return factory.getRepository(AccountRepository.class);
    }

    @Bean("UserDetailRepository")
    public UserDetailRepository detailsRepository() {
        LOGGER.info("Create " +UserDetailRepository.class.getName() +" repository bean.");
        return this.factory.getRepository(UserDetailRepository.class);
    }

    /**
     * JPA {@link EntityManager} autowiring.
     * @param em - {@link EntityManager} for default unit.
     */
    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        LOGGER.debug("Get " +em.getClass().getName() +" from persistence context.");
        this.em = em;
        LOGGER.debug("Create " +JpaRepositoryFactory.class.getName() +" factory for repositories.");
        this.factory = new JpaRepositoryFactory(this.em);
        this.factory.setQueryLookupStrategyKey(QueryLookupStrategy.Key.CREATE_IF_NOT_FOUND);
    }

}
