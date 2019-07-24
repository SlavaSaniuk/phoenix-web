package units.models;

import com.phoenix.models.User;
import com.phoenix.models.UserDetail;
import com.phoenix.models.wrappers.UserWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UserWrapperTestCase {

    @Test
    void getFullName_newUser_shouldReturnFullName() {
        UserDetail detail = new UserDetail();
        detail.setUserFname("Slavka");
        detail.setUserLname("Saniuk");

        User user = new User();
        user.setUserDetail(detail);

        UserWrapper wrapper = new UserWrapper(user);
        Assertions.assertEquals("Slavka Saniuk", wrapper.getFullName());
    }
}
