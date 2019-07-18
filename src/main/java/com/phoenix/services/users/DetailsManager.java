package com.phoenix.services.users;

import com.phoenix.models.User;
import com.phoenix.models.UserDetail;
import com.phoenix.repositories.UserDetailRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

/**
 * Implementation {@link DetailsService} service bean.
 */
@Service
public class DetailsManager implements DetailsService {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(DetailsManager.class);
    //Spring beans
    private UserDetailRepository repository; //Autowired in constructor

    /**
     * Constructor new {@link DetailsManager} service bean. Map {@link UserDetailRepository} repository to this bean.
     * @param repository - {@link UserDetailRepository} bean implementation.
     */
    //Constructor
    public DetailsManager(UserDetailRepository repository) {

        LOGGER.debug("Start to create " +getClass().getName() +" service bean");
        LOGGER.debug("Autowire: " +repository.getClass().getName() +" to " +getClass().getName());
        this.repository = repository;
    }

    @Override
    public int registerNewDetail(UserDetail detail, User user) throws EntityNotFoundException {

        if (user.getUserId() == 0) throw new EntityNotFoundException("User entity must before persisted");

        //MapsId
        user.setUserDetail(detail);
        detail.setDetailOwner(user);

        //Persist details
        detail = this.repository.save(detail);

        return detail.getDetailId();
    }
}
