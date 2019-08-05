package units.services.posts;

import com.phoenix.exceptions.NotPersistedEntity;
import com.phoenix.models.Post;
import com.phoenix.models.User;
import com.phoenix.repositories.posts.PostRepository;
import com.phoenix.services.posts.PostServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

class PostServiceImplTestCase {

    @Mock
    private PostRepository repository;

    @InjectMocks
    private PostServiceImpl service;

    @BeforeEach
    void setUpBeforeEach() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void createPost_entityNotPersistedBefore_shouldThrowNotPersistedEntity() {
        User user = new User();
        Post post = new Post();

        post.setPostDate();
        post.setPostTime();

        Assertions.assertThrows(NotPersistedEntity.class, () -> this.service.createPost(post, user));
    }

    @Test
    void createPost_newPost_userEntityShouldHasThePost() {
        User user = new User();
        user.setUserId(23);
        user.setUserPosts(new ArrayList<>());

        Post post = new Post();

        Post post1 = post;
        post1.setPostId(2);

        BDDMockito.given(this.repository.save(post)).willReturn(post1);

        this.service.createPost(post, user);
        Assertions.assertTrue(user.getUserPosts().contains(post1));
    }

    @Test
    void createPost_newPost_postEntityShouldHasId() {

        User user = new User();
        user.setUserId(23);
        user.setUserPosts(new ArrayList<>());

        Post post = new Post();

        Post post1 = post;
        post1.setPostId(2);

        BDDMockito.given(this.repository.save(post)).willReturn(post1);

        Assertions.assertNotEquals(0, this.service.createPost(post, user).getPostId());
    }

    @Test
    void createPost_newPost_postEntityShouldHasUserOwner() {

        User user = new User();
        user.setUserId(23);
        user.setUserPosts(new ArrayList<>());

        Post post = new Post();

        Post post1 = post;
        post1.setPostId(2);

        BDDMockito.given(this.repository.save(post)).willReturn(post1);

        Assertions.assertNotNull(this.service.createPost(post, user).getPostOwner());

    }

}
