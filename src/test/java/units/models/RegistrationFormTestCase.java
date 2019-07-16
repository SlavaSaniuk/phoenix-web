package units.models;

import com.phoenix.models.Account;
import com.phoenix.models.forms.RegistrationForm;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationFormTestCase {

    private RegistrationForm form;

    @BeforeEach
    void setUpBeforeEach() {
        this.form = new RegistrationForm();
    }

    @AfterEach
    void tearDownAfterEach() {
        this.form = null;
    }

    @Test
    void createAccount_allFieldsAreValid_shouldReturnAccountEntity() {

        this.form.setEmail("test@test.com");
        this.form.setPassword("123456789");

        Account created = this.form.createAccount();

        Assertions.assertEquals("test@test.com", created.getAccountEmail());
        Assertions.assertEquals("123456789", created.getAccountPassword());

    }

    @Test
    void createAccount_allFieldsAreValid_shouldResetFormPassword() {

        this.form.setEmail("test@test.com");
        this.form.setPassword("123456789");

        this.form.createAccount();

        Assertions.assertNull(this.form.getPassword());

    }
}
