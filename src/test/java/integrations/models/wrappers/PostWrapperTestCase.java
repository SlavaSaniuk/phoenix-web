package integrations.models.wrappers;

import com.phoenix.configuration.PersistenceConfiguration;
import com.phoenix.models.Post;
import com.phoenix.models.User;
import com.phoenix.models.wrappers.PostsWrapper;
import com.phoenix.repositories.RepositoriesConfiguration;
import com.phoenix.repositories.UserRepository;
import com.phoenix.services.ServicesConfiguration;
import com.phoenix.services.posts.PostService;
import integrations.beans.TestDataSource;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@ActiveProfiles("DEVELOPMENT")
@SpringJUnitConfig(classes = {TestDataSource.class, PersistenceConfiguration.class,
        RepositoriesConfiguration.class, ServicesConfiguration.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PostWrapperTestCase {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PostService service;

    private PostsWrapper wrapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(PostWrapperTestCase.class);


    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    void addSomePosts_initializedFlagIsFalse_shouldRetrieveSomePostsFromEnd() {

        User user = this.repository.findById(2).get();
        this.wrapper = new PostsWrapper(user);

        if (!this.wrapper.isInitialized()) {
            List<Post> posts = this.service.getSomeUserPostsFromTheEnd(wrapper.getUser(), 5);
            this.wrapper.addSomePosts(posts);
        }

        Assertions.assertTrue(this.wrapper.isInitialized());
        Assertions.assertEquals(5, this.wrapper.getUsersPosts().size());


        List<Post> posts = this.wrapper.getUsersPosts();

        Iterator<Post> it = posts.iterator();
        while (it.hasNext()) {
            Post p = it.next();
            LOGGER.debug(p.toString());

        }


    }
}
