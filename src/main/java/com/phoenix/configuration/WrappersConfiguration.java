package com.phoenix.configuration;

import com.phoenix.models.wrappers.UserWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

@Configuration
public class WrappersConfiguration {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(WrappersConfiguration.class);

    @Bean("UserWrapper")
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public UserWrapper userWrapper() {
        LOGGER.info("Start to create " +UserWrapper.class.getName() + " wrapper (session scoped) bean.");
        return new UserWrapper();
    }

}
