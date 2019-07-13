package com.phoenix.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ResourceBundle;

/**
 * {@link Configuration} class. This class define all beans that implements validation annotations.
 * Class {@link Override} method to configure application {@link Validator} bean.
 */
@Configuration
@PropertySource("classpath:/configuration-files/security.properties")
public class ValidationConfiguration implements WebMvcConfigurer {

    //logger
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidationConfiguration.class);

    //Spring beans
    private Environment environment; //autowired

    /**
     * Constructor uses to inform admin about validation initialization process.
     * Application load security properties from "security.properties" file in this {@link Environment} environment.
     * @param env - Spring {@link Environment}. Autowired.
     */
    @Autowired
    public ValidationConfiguration(Environment env) {
        LOGGER.info("Start to initialize " +getClass().getName() +" configuration class.");

        LOGGER.debug(getClass().getName() +": Load security properties.");
        this.environment = env;
    }

    /**
     * {@link MessageSource} bean. User as messages provider for validation logic
     * in application. Basename link on "classpath:static/lang/validation" resource bundle.
     * @return - {@link ReloadableResourceBundleMessageSource} configured bean.
     */
    @Bean("validationMessageSource")
    public MessageSource validationMessageSource() {

        LOGGER.info("Create " + ResourceBundle.class.getName() +" for validation localized messages.");
        ReloadableResourceBundleMessageSource msg_src = new ReloadableResourceBundleMessageSource();

        //Set parameters
        LOGGER.debug(msg_src.getClass().getName() +": Cache seconds: 60");
        msg_src.setCacheSeconds(60);

        LOGGER.debug(msg_src.getClass().getName() +": Basename - resources/static/lang/validation.*");
        msg_src.setBasename("classpath:static/lang/validation");

        LOGGER.debug(msg_src.getClass().getName() +": Default encoding: UTF-8" );
        msg_src.setDefaultEncoding("UTF-8");

        LOGGER.debug(msg_src.getClass().getName() +"was created.");
        return msg_src;
    }

    @Override
    public Validator getValidator() {
        LOGGER.info("Create " +Validator.class.getName() +" validation bean.");


        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        LOGGER.debug(Validator.class.getName() +" is implemented by " +validator.getClass().getName() +" class.");

        validator.setValidationMessageSource(this.validationMessageSource());
        LOGGER.debug(validator.getClass().getName() +": Message source - validation.");

        LOGGER.debug(validator.getClass().getName() +" was created.");
        return validator;
    }

    /**
     * {@link PasswordProperties} bean load and hold password constraints that's defined in security.properties file
     * with "com.phoenix.security.password.*" keys.
     * @return - {@link PasswordProperties} properties bean, that contain password properties.
     */
    @Bean("PasswordProperties")
    public PasswordProperties initPasswordProperties() {
        LOGGER.info("Create " +PasswordProperties.class.getName() +" properties bean.");
        return new PasswordProperties(this.environment);
    }

}
