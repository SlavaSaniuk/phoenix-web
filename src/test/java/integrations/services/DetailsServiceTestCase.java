package integrations.services;

import com.phoenix.configuration.PersistenceConfiguration;
import com.phoenix.models.User;
import com.phoenix.models.UserDetail;
import com.phoenix.repositories.RepositoriesConfiguration;
import com.phoenix.repositories.UserRepository;
import com.phoenix.services.ServicesConfiguration;
import com.phoenix.services.users.DetailsService;
import integrations.beans.TestDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@ActiveProfiles("TEST")
@SpringJUnitConfig(classes = {TestDataSource.class, PersistenceConfiguration.class, RepositoriesConfiguration.class, ServicesConfiguration.class})
class DetailsServiceTestCase {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private DetailsService service;

    @Autowired
    private UserRepository repository;

    @Test
    void registerNewUserDetail_userIsNotPersistedBefore_shouldThrowEntityNotFoundException() {
        User user = new User();
        Assertions.assertThrows(EntityNotFoundException.class, () -> this.service.registerNewDetail(new UserDetail(), user));
    }

    @Test
    @Transactional(rollbackFor = EntityNotFoundException.class)
    @Commit
    void registerNewUserDetail_userIsPersisted_shouldReturnUserDetailWithGeneratedId() {
        User user = new User();
        user = this.repository.save(user);

        UserDetail detail = new UserDetail();
        detail.setUserFname("Fname-service");
        detail.setUserLname("Lname-service");
        detail.setUserBirthday(LocalDate.of(1972,2,10));
        detail.setUserSex('m');

        detail = this.service.registerNewDetail(detail, user);

        Assertions.assertEquals(user.getUserId(), detail.getDetailId());
    }
}
