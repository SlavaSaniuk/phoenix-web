package integrations.services;

import com.phoenix.configuration.PersistenceConfiguration;
import com.phoenix.exceptions.EmailAlreadyRegisterException;
import com.phoenix.exceptions.JpaEngineException;
import com.phoenix.models.Account;
import com.phoenix.models.User;
import com.phoenix.models.forms.RegistrationForm;
import com.phoenix.repositories.RepositoriesConfiguration;
import com.phoenix.services.ServicesConfiguration;
import com.phoenix.services.accounting.SigningService;
import integrations.beans.TestDataSource;
import java.time.LocalDate;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("TEST")
@SpringJUnitConfig(classes = {TestDataSource.class, PersistenceConfiguration.class, RepositoriesConfiguration.class, ServicesConfiguration.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SigningServiceTestCase {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private SigningService service;

    @Test
    @Order(1)
    @Transactional(rollbackFor = EmailAlreadyRegisterException.class)
    @DisplayName("SetUp before all")
    @Commit
    @Disabled
    void setUpBeforeAll() {
        RegistrationForm form = new RegistrationForm();
        form.setEmail("test-signing@test.com");
        form.setPassword("a1234567");
        form.setfName("test");
        form.setlName("test");
        form.setBirthDay(LocalDate.now());
        form.setSex('f');
        try {
            this.service.signUp(form);
        }catch (EmailAlreadyRegisterException | JpaEngineException exc) {

        }
    }

    @Test
    void signIn_emailNotRegistered_shouldReturnNull() {
        Account account = new Account();
        account.setAccountEmail("EMAIL");

        Assertions.assertNull(this.service.signIn(account));
    }

    @Test
    void signIn_passwordIsIncorrect_shouldReturnNull() {
        Account account = new Account();
        account.setAccountEmail("test-signing@test.com");
        account.setAccountPassword("INCORRECT");
        Assertions.assertNull(this.service.signIn(account));
    }

    @Test
    void signIn_passwordCorrect_shouldReturnUser() {
        Account account = new Account();
        account.setAccountEmail("test-signing@test.com");
        account.setAccountPassword("a1234567");
        Assertions.assertNotNull(this.service.signIn(account));
    }

    @Test
    void signIn_passwordCorrect_shouldSetId() {
        Account account = new Account();
        account.setAccountEmail("test-signing@test.com");
        account.setAccountPassword("a1234567");
        User user = this.service.signIn(account);
        Assertions.assertEquals(11, user.getUserId());
    }




}
