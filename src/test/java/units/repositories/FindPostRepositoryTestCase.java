package units.repositories;

import com.phoenix.exceptions.NotPersistentEntityException;
import com.phoenix.models.Account;
import com.phoenix.models.Post;
import com.phoenix.models.User;
import com.phoenix.repositories.posts.PostRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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

        List<Post> result = this.repository.findSomePostsByOwner(user, 2);

        Assertions.assertEquals(0, result.size());
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

    @ParameterizedTest
    @ValueSource(ints = {0, -2, -123})
    void findSomePostsByUser_limitIsZero_shouldReturnTypedList(int limit) {

        User user = new User();
        user.setUserId(3);

        List test = new ArrayList<>();
        test.add(new Post());
        test.add(new Post());

        Query mock = Mockito.mock(Query.class);
        BDDMockito.given(this.em.createQuery(Mockito.anyString())).willReturn(mock);
        BDDMockito.given(mock.getResultList()).willReturn(test);

        List<Post> result = this.repository.findSomePostsByOwner(user, limit);
        Assertions.assertNotNull(result);
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(2, result.size());
    }

    @Test
    void findSomePostsByUserFromTheEnd_userIsNull_shouldThrowNPE() {
        Assertions.assertThrows(NullPointerException.class, () -> this.repository.findSomePostsByOwnerFromTheEnd(null, 0));
    }

    @Test
    void findSomePostsByOwnerFromTheEnd_userIsNotPersistent_shouldThrowNotPersistentEntityException() {
        User user= new User();
        Assertions.assertThrows(NotPersistentEntityException.class, () -> this.repository.findSomePostsByOwnerFromTheEnd(user,  2));
    }

    @Test
    void findSomePostsByOwnerFromTheEnd_notPostFound_shouldReturnEmptyList() {

        User user = new User();
        user.setUserId(3);

        Query mock = Mockito.mock(Query.class);
        BDDMockito.given(this.em.createQuery(Mockito.anyString())).willReturn(mock);
        BDDMockito.given(mock.getResultList()).willReturn(new ArrayList());
        Assertions.assertTrue(this.repository.findSomePostsByOwnerFromTheEnd(user, 2).isEmpty());
    }

    @Test
    void findSomePostsByOwnerFromTheEnd_listOfPost_shouldReturnTypedListWhichStartWithEnd() {

        User user = new User();
        user.setUserId(3);

        List posts = new ArrayList();

        Post p1 = new Post();
        p1.setPostId(1);
        Post p2 = new Post();
        p2.setPostId(2);
        Post p3 = new Post();
        p3.setPostId(3);

        posts.add(p2);
        posts.add(p1);


        Query mock = Mockito.mock(Query.class);
        BDDMockito.given(this.em.createQuery(Mockito.anyString())).willReturn(mock);
        BDDMockito.given(mock.getResultList()).willReturn(posts);

        List<Post> result = this.repository.findSomePostsByOwnerFromTheEnd(user, 2);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(Post.class, result.get(0).getClass());
        Assertions.assertEquals(2, result.get(0).getPostId());

    }

    @Test
    void findSomePostsByOwnerFromTheEnd_limitSetTo0_shouldReturnTypedListWhichStartWithEndOfAllPosts() {

        User user = new User();
        user.setUserId(3);

        List posts = new ArrayList();

        Post p1 = new Post();
        p1.setPostId(1);
        Post p2 = new Post();
        p2.setPostId(2);
        Post p3 = new Post();
        p3.setPostId(3);

        posts.add(p3);
        posts.add(p2);
        posts.add(p1);

        Query mock = Mockito.mock(Query.class);
        BDDMockito.given(this.em.createQuery(Mockito.anyString())).willReturn(mock);
        BDDMockito.given(mock.getResultList()).willReturn(posts);

        List<Post> result = this.repository.findSomePostsByOwnerFromTheEnd(user, 0);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(3, result.size());
        Assertions.assertEquals(Post.class, result.get(0).getClass());
        Assertions.assertEquals(3, result.get(0).getPostId());

    }

}
