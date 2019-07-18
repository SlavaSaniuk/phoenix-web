package units.services.users;

import com.phoenix.models.User;
import com.phoenix.models.UserDetail;
import com.phoenix.repositories.UserDetailRepository;
import com.phoenix.services.users.DetailsManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;

class DetailManagerTestCase{

    @Mock
    private UserDetailRepository repository;

    @InjectMocks
    private DetailsManager service;

    @BeforeEach
    void setUpBeforeEach() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void registerNewUser_UserIsNotPersisted_shouldThrowsEntityNotFoundsException() {
        User user = new User();
        UserDetail detail = new UserDetail();
        Assertions.assertThrows(EntityNotFoundException.class, () -> this.service.registerNewDetail(detail, user));
    }

    @Test
    void registerNewUser_UserWillBeBeforePersisted_shouldReturnNewUserDetailWithId() {

        int expected = 23;
        User for_save = new User();
        for_save.setUserId(expected);

        UserDetail detail = new UserDetail();
        detail.setDetailId(expected);

        UserDetail for_reg = new UserDetail();
        BDDMockito.given(this.repository.save(for_reg)).willReturn(detail);
        Assertions.assertEquals(expected, detail.getDetailId());
    }

}
