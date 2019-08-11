package units.models;

import com.phoenix.models.User;
import com.phoenix.models.UserDetail;


import org.junit.jupiter.api.Test;

@Deprecated
class UserWrapperTestCase {

    @Test
    void getFullName_newUser_shouldReturnFullName() {
        UserDetail detail = new UserDetail();
        detail.setUserFname("Slavka");
        detail.setUserLname("Saniuk");

        User user = new User();
        user.setUserDetail(detail);
    }
}
