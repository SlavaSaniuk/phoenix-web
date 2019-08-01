package units.services.utilities;

import com.phoenix.models.Post;
import com.phoenix.models.User;
import com.phoenix.services.utilities.JpaCaster;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class JpaCasterTestCase {

    @Test
    void castObjectsListToType_parameterOneIsNull_shouldThrowIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> JpaCaster.castObjectsListToType(null, new ArrayList()));
    }

    @Test
    void castObjectsListToType_parameterTwoIsNull_shouldThrowIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> JpaCaster.castObjectsListToType(User.class, null));
    }

    @Test
    void castObjectsListToType_emptyList_shouldReturnAnEmptyList() {
        List test = new ArrayList();
        List<User> result = JpaCaster.castObjectsListToType(User.class, test);
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void castObjectsListToType_listContainANonTypeElement_shouldThrowAClassCastException() {

        List test = new ArrayList();

        test.add(new User());
        test.add(new Post());

        Assertions.assertThrows(ClassCastException.class, () -> JpaCaster.castObjectsListToType(User.class, test));
    }

    @Test
    void castObjectsListToType_newListOfUserType_shouldReturnTypedList() {

        List test = new ArrayList();

        test.add(new User());
        test.add(new User());

        List<User> result = JpaCaster.castObjectsListToType(User.class, test);

        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(User.class, result.get(0).getClass());
    }



}
