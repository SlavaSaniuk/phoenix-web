package units.models;

import com.phoenix.models.Post;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PostEntityTestCase {

    @Test
    void setDate_newPost_shouldUseCurrentDate() {
        Post post = new Post();
        post.setPostDate();
        Assertions.assertEquals(LocalDate.now(), post.getPostDate());
    }

    @Test
    void setTime_newPost_shouldUseCurrentTime() {
        Post post = new Post();
        post.setPostTime();
        Assertions.assertEquals(LocalTime.now(), post.getPostTime());
    }

}
