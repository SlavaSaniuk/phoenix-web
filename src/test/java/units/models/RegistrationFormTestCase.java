package units.models;

import com.phoenix.models.Account;
import com.phoenix.models.UserDetail;
import com.phoenix.models.forms.RegistrationForm;
import com.phoenix.webmvc.formatters.LocalDateFormatter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

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

    @Test
    void createUserDetail_allFieldsAreValid_shouldReturnDetailEntity() {
        this.form.setfName("fname");
        this.form.setlName("lname");
        this.form.setBirthDay(LocalDate.now());
        this.form.setSex("F");

        UserDetail test = this.form.createUserDetail();

        Assertions.assertEquals("fname", test.getUserFname());
        Assertions.assertEquals(0, test.getUserAge());
    }

    @Test
    void createUserDetail_birthdayIsInvalid_shouldSetUserAgeToZero() {

        this.form.setBirthDay(LocalDateFormatter.INVALID);
        this.form.setSex("Female");

        UserDetail test = this.form.createUserDetail();

        Assertions.assertEquals(0, test.getUserAge());
        Assertions.assertEquals('F', test.getUserSex());
    }


    @Test
    void createUserDetail_allFieldsAreValid_shouldCalculateUserAge() {
        this.form.setBirthDay(LocalDate.of(1997, 1, 10));
        this.form.setSex("Male");
        UserDetail test = this.form.createUserDetail();

        Assertions.assertEquals(22, test.getUserAge());
        Assertions.assertEquals('M', test.getUserSex());
    }
}
