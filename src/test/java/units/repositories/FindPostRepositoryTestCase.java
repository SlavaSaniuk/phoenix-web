package units.repositories;

import com.phoenix.models.Account;
import com.phoenix.models.Post;
import com.phoenix.models.User;
import com.phoenix.repositories.posts.PostRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
@ExtendWith(MockitoExtension.class)
class FindPostRepositoryTestCase {

    @Mock
    private EntityManager em;


    @InjectMocks
    private PostRepositoryImpl repository;

    @BeforeEach
    void setUpBeforeEach() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void findSomePostsByUser_userIsNull_shouldThrowIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.repository.findSomePostsByOwner(null, 2));
    }

    @Test
    void findSomePostsByUser_userIdIsDefault_shouldThrowIllegalArgumentException() {
        User user = new User();
        Assertions.assertThrows(IllegalArgumentException.class, () -> this.repository.findSomePostsByOwner(user, 2));
    }

    @Test
    void findSomePostsByUser_notPostFound_shouldReturnEmptyList() {

        User user = new User();
        user.setUserId(3);

        Query mock = Mockito.mock(Query.class);
        BDDMockito.given(this.em.createQuery(Mockito.anyString())).willReturn(mock);
        BDDMockito.given(mock.getResultList()).willReturn(new ArrayList());
        Assertions.assertTrue(this.repository.findSomePostsByOwner(user, 2).isEmpty());

    }

    @Test
    void findSomePostsByUser_resultArrayWithDifferentElement_shouldReturnNull() {

        User user = new User();
        user.setUserId(3);

        List test = new ArrayList<>();
        test.add(new Account());

        Query mock = Mockito.mock(Query.class);
        BDDMockito.given(this.em.createQuery(Mockito.anyString())).willReturn(mock);
        BDDMockito.given(mock.getResultList()).willReturn(test);
        Assertions.assertNull(this.repository.findSomePostsByOwner(user, 2));

    }


    @Test
    void findSomePostsByUser_resultArrayContainAPost_shouldReturnTypedList() {

        User user = new User();
        user.setUserId(3);

        List test = new ArrayList<>();
        test.add(new Post());

        Query mock = Mockito.mock(Query.class);
        BDDMockito.given(this.em.createQuery(Mockito.anyString())).willReturn(mock);
        BDDMockito.given(mock.getResultList()).willReturn(test);

        List<Post> result = this.repository.findSomePostsByOwner(user, 2);
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(Post.class, result.get(0).getClass());
    }

}
