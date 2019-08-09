package integrations.repositories;

import com.phoenix.configuration.PersistenceConfiguration;
import com.phoenix.models.Post;
import com.phoenix.models.User;
import com.phoenix.repositories.RepositoriesConfiguration;
import com.phoenix.repositories.UserRepository;
import com.phoenix.repositories.posts.PostRepository;
import integrations.beans.TestDataSource;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@ActiveProfiles("TEST")
@SpringJUnitConfig(classes = {TestDataSource.class, PersistenceConfiguration.class, RepositoriesConfiguration.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PostRepositoryTestCase {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(PostRepositoryTestCase.class);

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private PostRepository repository;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private UserRepository user_repository;

    @Test
    @Order(1)
    @Transactional
    @Commit
    @Disabled
    void setUpBeforeAll() {
        Post one = new Post();
        one.setPostDate();
        one.setPostTime();
        one.setPostText("Hello world!");

        Post two = new Post();
        two.setPostTime();
        two.setPostDate();
        two.setPostText("I am a post tex.");

        Post three = new Post();
        three.setPostDate();
        three.setPostTime();
        three.setPostText("Good luck, baby!");

        Optional<User> user = this.user_repository.findById(1);
        User owner = user.get();

        List<Post> posts = new ArrayList<>();
        posts.add(one);
        posts.add(two);
        posts.add(three);
        owner.setUserPosts(posts);

        one.setPostOwner(owner);
        this.repository.save(one);

        two.setPostOwner(owner);
        this.repository.save(two);

        three.setPostOwner(owner);
        this.repository.save(three);

    }

    @Test
    void findSomePostsByOwner_limitIsSet_shouldReturnListWithSpecifiedSize() {

        Optional<User> opt = this.user_repository.findById(1);
        User user = opt.get();

        List<Post> posts = this.repository.findSomePostsByOwner(user, 5);

        Iterator<Post> iterator = posts.iterator();
        while (iterator.hasNext()) {
            Post current = iterator.next();
            LOGGER.debug("Post " +current.getPostId() +": " +current.getPostText());
        }

        Assertions.assertEquals(5, posts.size());

    }

    @Test
    void findSomePostsByOwner_userDontHaveAPosts_shouldReturnEmptyList() {

        Optional<User> opt = this.user_repository.findById(5);
        User user = opt.get();

        List<Post> posts = this.repository.findSomePostsByOwner(user, 5);

        Assertions.assertTrue(posts.isEmpty());
    }

    @Test
    void findAllPostsByOwner_limitIsSet_shouldReturnListWithAllPosts() {

        Optional<User> opt = this.user_repository.findById(1);
        User user = opt.get();

        List<Post> posts = this.repository.findAllPostsByOwner(user);

        Iterator<Post> iterator = posts.iterator();
        while (iterator.hasNext()) {
            Post current = iterator.next();
            LOGGER.debug("Post " +current.getPostId() +": " +current.getPostText());
        }

        Assertions.assertEquals(7, posts.size());

    }

    @Test
    void findSomePostsByOwnerFromTheEnd_limitIsSet_shouldReturnListWithDefinedSize() {

        User user = this.user_repository.findById(1).get();
        List<Post> result = this.repository.findSomePostsByOwnerFromTheEnd(user, 5);

        Assertions.assertEquals(5, result.size());
        Assertions.assertEquals(7, result.get(0).getPostId());

    }

    @Test
    void findSomePostsByOwnerFromTheEnd_limitIsNotSet_shouldReturnListWithAllPaosts() {

        User user = this.user_repository.findById(1).get();
        List<Post> result = this.repository.findSomePostsByOwnerFromTheEnd(user, 0);

        Assertions.assertEquals(7, result.size());
        Assertions.assertEquals(7, result.get(0).getPostId());

    }



}
