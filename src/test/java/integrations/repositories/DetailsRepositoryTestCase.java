package integrations.repositories;

import com.phoenix.configuration.PersistenceConfiguration;
import com.phoenix.models.User;
import com.phoenix.models.UserDetail;
import com.phoenix.repositories.RepositoriesConfiguration;
import com.phoenix.repositories.UserDetailRepository;
import com.phoenix.repositories.UserRepository;
import integrations.beans.TestDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ActiveProfiles("TEST")
@SpringJUnitConfig(classes = {TestDataSource.class, PersistenceConfiguration.class, RepositoriesConfiguration.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DetailsRepositoryTestCase {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private UserDetailRepository repository;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired()
    private UserRepository user_repository;

    @Test
    @Transactional
    @Commit
    @Order(1)
    void save_newUserDetail_shouldReturnGeneratedId() {

        User user = this.user_repository.save(new User());

        UserDetail detail = new UserDetail();
        detail.setUserFname("Fname");
        detail.setUserLname("Lname");
        detail.setUserBirthday(LocalDate.now());
        detail.setUserSex('f');

        user.setUserDetail(detail);
        detail.setDetailOwner(user);

        UserDetail test = this.repository.save(detail);
        Assertions.assertNotEquals(0, test.getDetailId());
    }

    @Test
    @Order(2)
    void findById_id3_shouldReturnNull() {
        Assertions.assertFalse(this.repository.findById(3).isPresent());
    }

    @Test
    @Order(3)
    void findById_id1_shouldReturnDetail() {
        Optional<UserDetail> opt = this.repository.findById(19);

        Assertions.assertTrue(opt.isPresent());

        UserDetail detail = opt.get();
        Assertions.assertEquals("Fname", detail.getUserFname());

    }

    @Test
    @Order(4)
    void findAll_twoSavedDetail_assertReurnListWithSize2() {
        List<UserDetail> founded = (List<UserDetail>) this.repository.findAll();
        Assertions.assertEquals(2, founded.size());
    }

}
