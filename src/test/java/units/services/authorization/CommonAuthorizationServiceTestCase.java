package units.services.authorization;

import com.phoenix.models.User;
import com.phoenix.services.authorization.CommonAuthorizationService;

import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

@SuppressWarnings("ConstantConditions")
@ExtendWith(MockitoExtension.class)
class CommonAuthorizationServiceTestCase {



    private final CommonAuthorizationService cas = new CommonAuthorizationService();

    @Test
    void authorizate_sessionIsNull_shouldThrowIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.cas.authorize(new User(), null));
    }

    @Test
    void authorizate_userIsNull_shouldSetNewUser() {
        HttpSession session = new MockHttpServletRequest().getSession(true);
        this.cas.authorize(null, session);
        Assertions.assertNotNull(session.getAttribute("current_user"));

    }

    @Test
    void authorizate_userIsNull_shouldSetAuthenticatedFlagToFalse() {
        HttpSession session = new MockHttpServletRequest().getSession(true);
        this.cas.authorize(null, session);
        Assertions.assertFalse((Boolean) session.getAttribute("authenticated"));
    }

    @Test
    void authorizate_newUser_sessionShouldContainUserAttribute() {
        HttpSession session = new MockHttpServletRequest().getSession(true);
        this.cas.authorize(new User(), session);
        Assertions.assertNotNull(session.getAttribute("current_user"));
    }

    @Test
    void authorizate_newUser_sessionShouldContainAuthenticatedFlag() {
        HttpSession session = new MockHttpServletRequest().getSession(true);
        this.cas.authorize(new User(), session);
        Assertions.assertNotNull(session.getAttribute("authenticated"));
    }

    @Test
    void authorizate_newUser_authenticatedFlagShoulBeTrue() {
        HttpSession session = new MockHttpServletRequest().getSession(true);
        this.cas.authorize(new User(), session);
        Assertions.assertTrue((Boolean) session.getAttribute("authenticated"));
    }

    @Test
    void deAuthorize_sessionIsNull_shouldThrowIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.cas.deAuthorize(null));
    }

}
