package com.phoenix.integration.repositoriestest;

import com.phoenix.configuration.PersistenceConfiguration;
import com.phoenix.integration.TestDataSourceConfig;
import com.phoenix.models.User;
import com.phoenix.repositories.RepositoriesConfiguration;
import com.phoenix.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@SpringJUnitConfig(classes = {
        TestDataSourceConfig.class,
        PersistenceConfiguration.class,
        RepositoriesConfiguration.class
})
@ActiveProfiles("TEST")
class UserRepositoryTest {

    private UserRepository repository;

    @Resource
    void setRepository(UserRepository repository) {
        this.repository = repository;
    }

    @Test
    @Transactional
    @Commit
    void save_savesNewUser_ShouldGenerateId() {
        User user = new User();
        Assertions.assertNotEquals(0, this.repository.save(user));
    }

    @Test
    void findAll_getAllUser_shouldReturnList() {
        List<User> users = (List<User>) this.repository.findAll();
        Assertions.assertNotEquals(0, users.size());
    }



}
