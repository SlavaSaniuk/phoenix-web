package units.services.accounting;

import com.phoenix.exceptions.JpaEngineException;
import com.phoenix.models.relation.users.Account;
import com.phoenix.models.relation.users.User;
import com.phoenix.repositories.UserRepository;
import com.phoenix.services.accounting.AccountManagementService;
import com.phoenix.services.accounting.SignAuthenticator;
import com.phoenix.services.users.DetailsService;
import java.util.Optional;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SignAuthenticatorTestCase {

    @Mock
    private UserRepository repository;

    @Mock
    private AccountManagementService ams;

    @Mock
    private DetailsService service;

    @InjectMocks
    private SignAuthenticator authenticator;

    private Account account = new Account();

    @BeforeEach
    void setUpBeforeEach() {
        MockitoAnnotations.initMocks(this);
        try {
            this.authenticator.afterPropertiesSet();
        } catch (Exception exc) {
            System.exit(1);
        }
        this.account = new Account();
        account.setAccountEmail("test@test.com");
        account.setAccountPassword("12345679a");
    }

    @Test
    @Order(1)
    @DisplayName("signIn - login form null")
    void signIn_loginFormIsNull_shouldThrowIllegalArgumentException() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> this.authenticator.signIn(null));

    }

    @Test
    @Order(2)
    @DisplayName("signIn - email form null")
    void signIn_emailIsNull_shouldThrowIllegalArgumentException() {
        this.account.setAccountEmail(null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.authenticator.signIn(this.account));
    }

    @Test
    @Order(3)
    @DisplayName("signIn - email not registered")
    void signIn_emailNotRegistered_shouldReturnNull() {

        BDDMockito.given(this.ams.authenticateAccount(Mockito.any(Account.class))).willReturn(false);
        Assertions.assertNull(this.authenticator.signIn(this.account));

    }

    @Test
    @Order(4)
    @DisplayName("signIn - passwordNotCorrect")
    void signIn_passwordNotCorrect_shouldReturnNull() {

        BDDMockito.given(this.ams.authenticateAccount(Mockito.any(Account.class))).willReturn(false);
        Assertions.assertNull(this.authenticator.signIn(this.account));

    }

    @Test
    @Order(5)
    @DisplayName("signIn - user is not found")
    void signIn_userNotFound_shouldThrowJpaEngineException() {

        BDDMockito.given(this.ams.authenticateAccount(Mockito.any(Account.class))).willReturn(true);
        BDDMockito.given(this.repository.findById(Mockito.anyInt())).willReturn(Optional.empty());

        Assertions.assertThrows(JpaEngineException.class, () -> this.authenticator.signIn(this.account));
    }

    @Test
    @Order(6)
    @DisplayName("signIn - allOk")
    void signIn_passwordCorrect_shouldReturnUser() {

        int expected = 23;
        this.account.setAccountId(expected);

        BDDMockito.given(this.ams.authenticateAccount(Mockito.any(Account.class))).willReturn(true);

        User user = new User();
        user.setUserId(expected);
        BDDMockito.given(this.repository.findById(Mockito.anyInt())).willReturn(Optional.of(user));

        User authenticated = this.authenticator.signIn(account);

        Assertions.assertNotNull(authenticated);
        Assertions.assertEquals(expected, authenticated.getUserId());

    }
}
