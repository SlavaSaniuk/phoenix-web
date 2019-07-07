package com.phoenix.integrationdeprecated.controllerstest;

import com.phoenix.configuration.PersistenceConfiguration;
import com.phoenix.configuration.WebContextConfiguration;
import com.phoenix.controllers.RegistrationController;
import com.phoenix.integrationdeprecated.TestDataSourceConfig;
import com.phoenix.repositories.RepositoriesConfiguration;
import com.phoenix.services.ServicesConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

@SpringJUnitWebConfig(classes = {
        TestDataSourceConfig.class,
        PersistenceConfiguration.class,
        RepositoriesConfiguration.class,
        ServicesConfiguration.class,
        WebContextConfiguration.class
})
@ActiveProfiles("TEST")
class RegistrationControllerTestCase {

    private MockMvc mvc_mock;

    @Resource
    private RegistrationController test_controller;

    @BeforeEach
    void setUpBeforeEach(WebApplicationContext wac) {
        this.mvc_mock = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void handleRegistrationRequest() throws Exception {
        this.mvc_mock.perform(MockMvcRequestBuilders.post("/registration")).andExpect(MockMvcResultMatchers.status().isOk());
    }
}
