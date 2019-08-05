package integrations.services;

import com.phoenix.configuration.PersistenceConfiguration;
import com.phoenix.models.Post;
import com.phoenix.models.User;
import com.phoenix.repositories.RepositoriesConfiguration;
import com.phoenix.repositories.UserRepository;
import com.phoenix.services.ServicesConfiguration;
import com.phoenix.services.posts.PostService;
import integrations.beans.TestDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
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

    @Test
    @Transactional
    @Commit
    void createPost_newPost_shouldSetAnId() {

        User owner;
        Optional<User> optional_owner = this.repository.findById(1);
        if (!optional_owner.isPresent()) {
            Assertions.assertEquals(0, 1);
            return;
        }else owner = optional_owner.get();

        Post new_post = new Post();
        new_post.setPostText("Text from service.");
        new_post.setPostDate();
        new_post.setPostTime();

        new_post = this.service.createPost(new_post, owner);
        Assertions.assertNotNull(new_post);
        Assertions.assertNotEquals(0, new_post.getPostId());

        LOGGER.debug("POST ID: " +new_post.getPostId());
    }
}
