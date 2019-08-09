package integrations.services;

import com.phoenix.configuration.PersistenceConfiguration;
import com.phoenix.exceptions.NotPersistedEntity;
import com.phoenix.models.Post;
import com.phoenix.models.User;
import com.phoenix.repositories.RepositoriesConfiguration;
import com.phoenix.repositories.UserRepository;
import com.phoenix.services.ServicesConfiguration;
import com.phoenix.services.posts.PostService;
import integrations.beans.TestDataSource;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SuppressWarnings({"SpringJavaInjectionPointsAutowiringInspection", "OptionalGetWithoutIsPresent"})
@ActiveProfiles("TEST")
@SpringJUnitConfig(classes = {TestDataSource.class, PersistenceConfiguration.class, RepositoriesConfiguration.class, ServicesConfiguration.class})
class PostServiceTestCase {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(PostServiceTestCase.class);
    //Spring beans
    @Autowired
    private PostService service;

    @Autowired
    private UserRepository repository;

    @ParameterizedTest
    @ValueSource(strings = {"Hello", "I am a post", "I am third post."})
    @Transactional
    @Commit
    @Disabled
    void createPost_newPost_shouldSetAnId(String text) {

        User owner;
        Optional<User> optional_owner = this.repository.findById(7);
        if (!optional_owner.isPresent()) {
            Assertions.assertEquals(0, 1);
            return;
        }else owner = optional_owner.get();

        Post new_post = new Post();
        new_post.setPostText(text);
        new_post.setPostDate();
        new_post.setPostTime();

        Post persisted = null;
        try {
            persisted = this.service.createPost(new_post, owner);
        }catch (NotPersistedEntity exc) {
            LOGGER.error(exc.getMessage());
        }

        Assertions.assertNotNull(persisted);
        Assertions.assertNotEquals(0, persisted.getPostId());

        LOGGER.debug("POST ID: " +new_post.getPostId());
    }

    @Test
    void getUserPosts_user7_shouldReturn3Posts() {

        User owner = this.repository.findById(7).get();

        List<Post> result = this.service.getUserPosts(owner);

        Assertions.assertEquals(3, result.size());


    }

    @Test
    void getSomeUserPosts_user7_shouldReturn2Posts() {

        User owner = this.repository.findById(7).get();

        List<Post> result = this.service.getSomeUserPosts(owner, 2);

        Assertions.assertEquals(2, result.size());


    }
}
