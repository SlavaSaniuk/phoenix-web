package integrations.services;

import com.phoenix.configuration.PersistenceConfiguration;
import com.phoenix.models.relation.users.Account;
import com.phoenix.models.relation.users.User;
import com.phoenix.repositories.RepositoriesConfiguration;
import com.phoenix.repositories.UserRepository;
import com.phoenix.services.ServicesConfiguration;
import com.phoenix.services.accounting.AccountManagementService;
import integrations.beans.TestDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@SpringJUnitConfig(classes = {TestDataSource.class, PersistenceConfiguration.class, RepositoriesConfiguration.class, ServicesConfiguration.class})
@ActiveProfiles("TEST")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AccountManagementServiceTestCase {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private AccountManagementService ams;

    @Autowired
    private UserRepository repository;

    @Test
    @Order(1)
    @DisplayName("SetUp before all")
    @Transactional
    @Commit
    void setUpBeforeAll() {

        String EMAIL = "ams-test@test.com";
        Account test = new Account();
        test.setAccountEmail(EMAIL);
        test.setAccountPassword("test-password");

        if (!this.ams.isRegistered(test)) {
            User user = new User();
            this.repository.save(user);
            this.ams.registerAccount(test, user);
        }

    }

    @Test
    @Order(2)
    @DisplayName("authenticateAccount_-_Not registered")
    void authenticateAccount_emailNotFound_shouldReturnFalse() {

        final String NOT_REGISTERED_EMAIL = "ams2-test@test.com";

        Account account = new Account();
        account.setAccountEmail(NOT_REGISTERED_EMAIL);

        Assertions.assertFalse(this.ams.authenticateAccount(account));
    }

    @Test
    @Order(3)
    @DisplayName("authenticateAccount_-_Password incorrect")
    void authenticateAccount_passwordIsIncorrect_shouldReturnFalse() {

        final String INCORRECT_PASSWORD = "test_password2";

        Account account = new Account();
        account.setAccountEmail("ams-test@test.com");
        account.setAccountPassword(INCORRECT_PASSWORD);

        Assertions.assertFalse(this.ams.authenticateAccount(account));
    }

    @Test
    @Order(4)
    @DisplayName("authenticateAccount_-_Password correct")
    void authenticateAccount_passwordIsCorrect_shouldReturnTrue() {

       String CORRECT_PASSWORD = "test-password";

        Account account = new Account();
        account.setAccountEmail("ams-test@test.com");
        account.setAccountPassword(CORRECT_PASSWORD);

        boolean actual = this.ams.authenticateAccount(account);

        Assertions.assertTrue(actual);
    }

    @Test
    @Order(5)
    @DisplayName("authenticateAccount_-_Password correct - 2")
    void authenticateAccount_passwordIsCorrect_shouldResetPassword() {

        String CORRECT_PASSWORD = "test-password";

        Account account = new Account();
        account.setAccountEmail("ams-test@test.com");
        account.setAccountPassword(CORRECT_PASSWORD);

        this.ams.authenticateAccount(account);

        Assertions.assertNull(account.getAccountPassword());
    }

    @Test
    @Order(6)
    @DisplayName("authenticateAccount_-_Password correct - 3")
    void authenticateAccount_passwordIsCorrect_shouldSetId() {

        String CORRECT_PASSWORD = "test-password";

        Account account = new Account();
        account.setAccountEmail("ams-test@test.com");
        account.setAccountPassword(CORRECT_PASSWORD);

        this.ams.authenticateAccount(account);

        Assertions.assertNotEquals(0, account.getAccountId());
    }


}
