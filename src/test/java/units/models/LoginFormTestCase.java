package units.models;

import com.phoenix.models.relation.users.Account;
import com.phoenix.models.forms.LoginForm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class LoginFormTestCase {

    @Test
    void createAccount_newLoginForm_shouldReturnNewAccount() {

        String email = "test@test.com";
        String password = "12345678a";

        LoginForm form = new LoginForm();
        form.setEmail(email);
        form.setPassword(password);

        Account account = form.createAccount();

        Assertions.assertNotNull(account);
        Assertions.assertEquals(email, account.getAccountEmail());
        Assertions.assertEquals(password, account.getAccountPassword());
    }

}
