package com.phoenix.integrationtests.configurationfortests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ValidationTestConfiguration implements WebMvcConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationTestConfiguration.class);

    /**
     * {@link MessageSource} bean. User as messages provider for validation logic
     * in application. Basename link on "classpath:static/lang/validation" resource bundle.
     * @return - {@link ReloadableResourceBundleMessageSource} configured bean.
     */
    @Bean("validationMessageSource")
    public MessageSource validationMessageSource() {

        ReloadableResourceBundleMessageSource msg_src = new ReloadableResourceBundleMessageSource();

        //Set parameters
        msg_src.setCacheSeconds(60);
        msg_src.setBasename("classpath:static/lang/validation");
        msg_src.setDefaultEncoding("UTF-8");

        return msg_src;
    }

    @Override
    @Bean
    public Validator getValidator() {

        LOGGER.info("Create " +Validator.class.getName() +" validation bean.");
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        LOGGER.debug(Validator.class.getName() +" is implemented by " +validator.getClass().getName() +" class.");

        LOGGER.debug(validator.getClass().getName() +": Set validation message source.");
        validator.setValidationMessageSource(this.validationMessageSource());

        LOGGER.debug(Validator.class.getName() +" was created.");
        return validator;
    }

}