package units.services.accounting;

import com.phoenix.models.Account;
import com.phoenix.repositories.AccountRepository;
import com.phoenix.services.accounting.AccountManager;
import com.phoenix.services.security.hashing.HashAlgorithms;
import com.phoenix.services.security.hashing.Hasher;
import org.junit.jupiter.api.*;
import org.mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AccountManagerTestCase {

    @Mock
    private AccountRepository repository;

    @InjectMocks
    private AccountManager manager;

    private final Hasher service = new Hasher();
    private Account test;
    private Account founed;

    @BeforeEach
    void setUpBeforeEach() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.service.setHashAlgorithm(HashAlgorithms.SHA_512);
        this.manager.setHashingService(this.service);
        this.manager.afterPropertiesSet();

        this.test = new Account();
        this.test.setAccountEmail("test@test.com");

        this.founed = new Account();
        this.founed.setAccountPasswordHash("4b02741ed88a0cebeee4f08937c46f42a34f2ac91c0954dc0cd944ca69b9c7170c7a2b16ec4d1a332dbf5ee3d08a79ca3d6e522300cf8e6aa2e0d6b6872055ba");
        this.founed.setAccountPasswordSalt("1c79ade1ea9bbb25cb1a00510f87331f0488864a08ff6dbb164e8601ddd3108cac5b9bcd73e80910a92fc3ed5fe2d2c60ea0a7065d846c75bb307cccd9e1af22");
    }

    @AfterEach
    void tearDownAfterEach() {
        this.test = null;
        this.founed = null;
    }

    @Test
    @Order(1)
    @DisplayName("preparePassword - generate password salt")
    void preparePassword_newPassword_shouldGeneratePasswordSalt() {
        Account test = new Account();
        test.setAccountPassword("test123");
        this.manager.preparePassword(test);
        Assertions.assertFalse(test.getAccountPasswordSalt().isEmpty());
    }

    @Test
    @Order(2)
    @DisplayName("preparePassword - generate password hash")
    void preparePassword_newPassword_shouldGeneratePasswordHash() {
        Account test = new Account();
        test.setAccountPassword("test123");
        this.manager.preparePassword(test);
        Assertions.assertFalse(test.getAccountPasswordHash().isEmpty());
    }

    @Test
    @Order(3)
    @DisplayName("preparePassword - reset account password")
    void preparePassword_newPassword_shouldResetAccountPassword() {
        Account test = new Account();
        test.setAccountPassword("test123");
        this.manager.preparePassword(test);
        Assertions.assertNull(test.getAccountPassword());
    }

    @Test
    @Order(4)
    @DisplayName("comparePassword - password incorrect")
    void comparePasswords_passwordIncorrect_shouldReturnFalse() {

        Account given = new Account();
        given.setAccountPassword("123");

        Account founded = new Account();
        founded.setAccountPasswordHash("4b02741ed88a0cebeee4f08937c46f42a34f2ac91c0954dc0cd944ca69b9c7170c7a2b16ec4d1a332dbf5ee3d08a79ca3d6e522300cf8e6aa2e0d6b6872055ba");
        founded.setAccountPasswordSalt("1c79ade1ea9bbb25cb1a00510f87331f0488864a08ff6dbb164e8601ddd3108cac5b9bcd73e80910a92fc3ed5fe2d2c60ea0a7065d846c75bb307cccd9e1af22");

        Assertions.assertFalse(this.manager.comparePassword(given, founded));
    }

    @Test
    @Order(5)
    @DisplayName("comparePassword - password correct")
    void comparePasswords_passwordCorrect_shouldReturnTrue() {

        Account given = new Account();
        given.setAccountPassword("test123");

        Account founded = new Account();
        founded.setAccountPasswordHash("4b02741ed88a0cebeee4f08937c46f42a34f2ac91c0954dc0cd944ca69b9c7170c7a2b16ec4d1a332dbf5ee3d08a79ca3d6e522300cf8e6aa2e0d6b6872055ba");
        founded.setAccountPasswordSalt("1c79ade1ea9bbb25cb1a00510f87331f0488864a08ff6dbb164e8601ddd3108cac5b9bcd73e80910a92fc3ed5fe2d2c60ea0a7065d846c75bb307cccd9e1af22");

        Assertions.assertTrue(this.manager.comparePassword(given, founded));
    }

    @Test
    @Order(6)
    @DisplayName("comparePassword - password correct - reset password field")
    void comparePasswords_passwordCorrect_shouldResetPasswordField() {

        Account given = new Account();
        given.setAccountPassword("test123");

        Account founded = new Account();
        founded.setAccountPasswordHash("4b02741ed88a0cebeee4f08937c46f42a34f2ac91c0954dc0cd944ca69b9c7170c7a2b16ec4d1a332dbf5ee3d08a79ca3d6e522300cf8e6aa2e0d6b6872055ba");
        founded.setAccountPasswordSalt("1c79ade1ea9bbb25cb1a00510f87331f0488864a08ff6dbb164e8601ddd3108cac5b9bcd73e80910a92fc3ed5fe2d2c60ea0a7065d846c75bb307cccd9e1af22");

        Assertions.assertTrue(this.manager.comparePassword(given, founded));
        Assertions.assertNull(given.getAccountPassword());
    }


    @Test
    @Order(7)
    @DisplayName("authenticateAccount - account not find")
    void authenticateAccount_accountNotFound_shouldReturnFalse() {
        BDDMockito.given(this.repository.findAccountByAccountEmail(this.test.getAccountEmail())).willReturn(null);
        Assertions.assertFalse(this.manager.authenticateAccount(this.test));
    }

    @Test
    @Order(8)
    @DisplayName("authenticateAccount - password is incorrect")
    void authenticateAccount_passwordIsIncorrect_shouldReturnFalse() {
        this.test.setAccountPassword("incorrect");
        BDDMockito.given(this.repository.findAccountByAccountEmail(this.test.getAccountEmail())).willReturn(this.founed);
        Assertions.assertFalse(this.manager.authenticateAccount(this.test));
    }

    @Test
    @Order(9)
    @DisplayName("authenticateAccount - password correct")
    void authenticateAccount_passwordCorrect_shouldReturnTrue() {
        this.test.setAccountPassword("test123");
        BDDMockito.given(this.repository.findAccountByAccountEmail(this.test.getAccountEmail())).willReturn(this.founed);
        Assertions.assertTrue(this.manager.authenticateAccount(this.test));
    }

    @Test
    @Order(10)
    @DisplayName("authenticateAccount - password correct - reset password")
    void authenticateAccount_passwordCorrect_shouldResetPassword() {
        this.test.setAccountPassword("test123");
        BDDMockito.given(this.repository.findAccountByAccountEmail(this.test.getAccountEmail())).willReturn(this.founed);
        this.manager.authenticateAccount(this.test);
        Assertions.assertNull(this.test.getAccountPassword());
    }





}
