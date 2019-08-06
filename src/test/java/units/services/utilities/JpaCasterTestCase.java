package units.services.utilities;


import com.phoenix.services.utilities.JpaCaster;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class JpaCasterTestCase {

    @Test
    void castObjectsListToType_typeParameterIsNull_shouldThrowNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> JpaCaster.castObjectsListToType(null, new ArrayList()));
    }

    @Test
    void castObjectsListToType_listParameterIsNull_shouldThrowNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () -> JpaCaster.castObjectsListToType(Integer.class, null));
    }

    @Test
    void castObjectsListToType_emptyList_shouldReturnEmptyList() {
        List<Integer> result = JpaCaster.castObjectsListToType(Integer.class, new ArrayList());
        Assertions.assertTrue(result.isEmpty());
    }

    @Test
    void castObjectsListToType_oneOfListArgumentIsDifferentType_shouldSkipThisElement() {

        List test = new ArrayList();
        test.add(1);
        test.add("Wrong type");
        test.add(3);

        List<Integer> result = JpaCaster.castObjectsListToType(Integer.class, test);

        Assertions.assertEquals(2, result.size());
    }

    @Test
    void castObjectsListToType_newList_shouldReturnTypedList() {

        List test = new ArrayList();
        test.add(1);
        test.add(3);

        List<Integer> result = JpaCaster.castObjectsListToType(Integer.class, test);

        Assertions.assertEquals(Integer.class, result.get(0).getClass());
    }



}
