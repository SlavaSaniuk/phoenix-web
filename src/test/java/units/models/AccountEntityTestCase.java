package units.models;

import com.phoenix.models.Account;
import com.phoenix.models.forms.RegistrationForm;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccountEntityTestCase {

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
    void constructor_validRegistrationForm_shouldReturnAccount() {

        this.form.setEmail("test@test.com");
        this.form.setPassword("123456789");

        Account created = new Account(this.form);

        Assertions.assertEquals("test@test.com", created.getAccountEmail());
        Assertions.assertEquals("123456789", created.getAccountPassword());

    }
}
