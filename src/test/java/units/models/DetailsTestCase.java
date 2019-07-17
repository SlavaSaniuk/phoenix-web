package units.models;

import com.phoenix.models.UserDetail;
import com.phoenix.models.forms.RegistrationForm;
import com.phoenix.webmvc.formatters.LocalDateFormatter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class DetailsTestCase {

    @Test
    void constructor_validRegForm_shouldReturnNewUserDetail() {

        final RegistrationForm form = new RegistrationForm();
        form.setfName("test");
        form.setlName("test2");
        form.setBirthDay(LocalDate.now());
        form.setSex('f');

        UserDetail test = new UserDetail(form);

        Assertions.assertEquals("test", test.getUserFname());
        Assertions.assertEquals("test2", test.getUserLname());
    }

    @Test
    void setUserBirthday_localDateNull_shouldSetAgeToZero() {

        UserDetail test = new UserDetail();
        test.setUserBirthday(null);

        Assertions.assertEquals(0, test.getUserAge());

    }

    @Test
    void setUserBirthday_localDateInvalid_shouldSetAgeToZero() {

        UserDetail test = new UserDetail();
        test.setUserBirthday(LocalDateFormatter.INVALID);

        Assertions.assertEquals(0, test.getUserAge());
    }

    @Test
    void setUserBirthday_newLocaldate_shouldSetUserAge() {

        UserDetail test = new UserDetail();
        test.setUserBirthday(LocalDate.of(2016, 9, 10));

        Assertions.assertEquals(2, test.getUserAge());
    }
}
