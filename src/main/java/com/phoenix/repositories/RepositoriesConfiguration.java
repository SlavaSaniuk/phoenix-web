package com.phoenix.repositories;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;

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

    /**
     * Default constructor. Inform about loading of this configuration class.
     */
    public RepositoriesConfiguration() {
        LOGGER.info("Start to initialize " +getClass().getName() +" configuration class");
    }

    /**
     * {@link UserRepository} bean. Spring automatically create this interface
     * implementation and return it as spring bean.
     * @return - {@link UserRepository} bean.
     */
    @Bean("UserRepository")
    public UserRepository userRepository() {

        JpaRepositoryFactory factory = new JpaRepositoryFactory(this.em);

        LOGGER.debug("Creating " +UserRepository.class.getName() +" implementation.");
        LOGGER.debug("Return " +UserRepository.class.getName() +" created bean.");
        return factory.getRepository(UserRepository.class);
    }

}
