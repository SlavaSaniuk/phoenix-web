package units.services.accounting;

import com.phoenix.exceptions.JpaEngineException;
import com.phoenix.models.Account;
import com.phoenix.models.User;
import com.phoenix.models.UserDetail;
import com.phoenix.models.forms.RegistrationForm;
import com.phoenix.repositories.UserRepository;
import com.phoenix.services.accounting.AccountManagementService;
import com.phoenix.services.accounting.SignAuthenticator;
import com.phoenix.services.users.DetailsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;

class SignAuthenticatorTestCase{

    @Mock
    private UserRepository repository;
    @Mock
    private AccountManagementService ams;
    @Mock
    private DetailsService details;

    @InjectMocks
    private SignAuthenticator service;

    private final RegistrationForm form = new RegistrationForm();

    @BeforeEach
    void setUpBeforeEach() {
        MockitoAnnotations.initMocks(this);
        this.service.setAccountManagementService(this.ams);
        this.service.setDetailsService(this.details);
        this.form.setEmail("email@email.com");
        this.form.setPassword("123456789");
        this.form.setfName("Fname");
        this.form.setlName("Lname");
        this.form.setBirthDay(LocalDate.now());
        this.form.setSex('f');
    }

    @Test
    void signUp_cannotSaveUser_shouldThrowJpaEngineException() {
        User user = new User();
        BDDMockito.given(this.ams.isRegistered(Mockito.any(Account.class))).willReturn(false);
        BDDMockito.given(this.repository.save(new User())).willReturn(user);
        Assertions.assertThrows(JpaEngineException.class, () ->  this.service.signUp(new RegistrationForm()));
    }

    @Test
    void signUp_generatedDetailsIdIsNotSameAsUserId_shouldThrowJpaEngineException() {

        int expected = 23;

        Account account = new Account();
        BDDMockito.given(this.ams.isRegistered(account)).willReturn(false);

        User user = new User();
        user.setUserId(expected);
        BDDMockito.given(this.repository.save(new User())).willReturn(user);

        BDDMockito.given(this.ams.registerAccount(account, user)).willReturn(expected);

        UserDetail detail = new UserDetail();
        detail.setDetailId(23);
        BDDMockito.given(this.details.registerNewDetail(Mockito.any(UserDetail.class), Mockito.any(User.class))).willReturn(24);
        Assertions.assertThrows(JpaEngineException.class, ()-> this.service.signUp(this.form));
    }

    @Test
    void signUp_newUserDetail_shouldReturnGeneratedUserId() {

        int expected = 23;
        BDDMockito.given(this.ams.isRegistered(Mockito.any(Account.class))).willReturn(false);

        User user = new User();
        user.setUserId(expected);

        BDDMockito.given(this.repository.save(new User())).willReturn(user);
        BDDMockito.given(this.ams.registerAccount(new Account(), user)).willReturn(expected);

        Mockito.when(this.details.registerNewDetail(Mockito.any(UserDetail.class), Mockito.any(User.class))).thenReturn(expected);
        Assertions.assertEquals(expected, this.service.signUp(new RegistrationForm()));
    }
}
