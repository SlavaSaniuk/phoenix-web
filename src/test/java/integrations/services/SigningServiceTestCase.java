package integrations.services;

import com.phoenix.configuration.PersistenceConfiguration;
import com.phoenix.exceptions.EmailAlreadyRegisterException;
import com.phoenix.models.forms.RegistrationForm;
import com.phoenix.repositories.RepositoriesConfiguration;
import com.phoenix.services.ServicesConfiguration;
import com.phoenix.services.accounting.SigningService;
import integrations.beans.TestDataSource;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

@SpringJUnitConfig(classes = {TestDataSource.class, PersistenceConfiguration.class, RepositoriesConfiguration.class, ServicesConfiguration.class})
@ActiveProfiles("TEST")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SigningServiceTestCase {

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    SigningService service;

    @Test
    @Transactional
    @Commit
    @Order(1)
    void signUp_newRegistrationForm_shouldReturnGeneratedId() {

        RegistrationForm form = new RegistrationForm();
        form.setEmail("test1@mail.ru");
        form.setPassword("123456789");

        int id = this.service.signUp(form);

        Assertions.assertNotEquals(0, id);
    }

    @Test
    @Transactional(rollbackFor = EmailAlreadyRegisterException.class)
    @Order(2)
    void signUp_accountAlreadyRegister_shouldThrowEmailAlreadyExistException() {

        RegistrationForm form = new RegistrationForm();
        form.setEmail("test1@mail.ru");
        form.setPassword("12345678910");

        Assertions.assertThrows(EmailAlreadyRegisterException.class, () -> this.service.signUp(form));
    }


}
