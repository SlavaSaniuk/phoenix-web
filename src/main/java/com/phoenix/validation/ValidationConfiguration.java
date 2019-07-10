package com.phoenix.validation;

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

@Configuration
@PropertySource("classpath:/configuration-files/security.properties")
public class ValidationConfiguration implements WebMvcConfigurer {

    private Environment environment;

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
    public Validator getValidator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(this.validationMessageSource());
        return validator;
    }

    @Bean("PasswordProperties")
    public PasswordProperties initPasswordProperties() {
        return new PasswordProperties(this.environment);
    }

    @Autowired
    public void setEnvironment(Environment env) {
        this.environment = env;
    }
}
