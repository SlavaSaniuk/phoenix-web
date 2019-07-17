package com.phoenix.webmvc;

import com.phoenix.webmvc.formatters.CharFormatter;
import com.phoenix.webmvc.validation.PasswordProperties;
import com.phoenix.webmvc.formatters.LocalDateFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;

@Configuration
@PropertySource("classpath:/configuration-files/security.properties")
public class WebMvcConfiguration implements WebMvcConfigurer {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(WebMvcConfiguration.class);
    //Spring beans
    private Environment environment;

    //Constructor
    @Autowired
    public WebMvcConfiguration(Environment env) {
        LOGGER.info("Start to initialize " +getClass().getName() +" configuration class.");
        LOGGER.debug("AUTOWIRE " +env.getClass() +" to " +getClass().getName() +" class.");
        this.environment = env;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {

        LOGGER.info("Create " +Formatter.class.getName() +" formatter bean for " +LocalDate.class.getName() +" type.");
        Formatter<LocalDate> localDate_formatter = new LocalDateFormatter();

        LOGGER.debug("Add " +localDate_formatter.getClass().getName() +" to " +FormatterRegistry.class.getName() +" formatters bean registry.");
        registry.addFormatter(localDate_formatter);

        LOGGER.info("Create " +Formatter.class.getName() +" formatter bean for " +Character.class.getName() +" type.");
        Formatter<Character> character_formatter = new CharFormatter();

        LOGGER.debug("Add " +character_formatter.getClass().getName() +" to " +FormatterRegistry.class.getName() +" formatters bean registry.");
        registry.addFormatter(character_formatter);

    }

    @Override
    public Validator getValidator() {

        //Create validator factory bean
        LOGGER.info("Create " +Validator.class.getName() +" validation bean.");
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        LOGGER.debug(Validator.class.getName() +" is implemented by " +validator.getClass().getName() +" class.");

        //Set parameters
        LOGGER.debug(validator.getClass().getName() +": Set validation message source.");
        validator.setValidationMessageSource(this.validationMessageSource());

        LOGGER.debug(validator.getClass().getName() +" was created.");
        return validator;
    }

    @Bean("ValidationSource")
    public MessageSource validationMessageSource() {

        //Create message source
        LOGGER.info("Create " +MessageSource.class.getName() + " validation bean.");
        ResourceBundleMessageSource src = new ResourceBundleMessageSource();
        LOGGER.debug(MessageSource.class.getName() +" is implemented by " +src.getClass().getName());

        //Set parameters
        LOGGER.debug(src.getClass().getName() +": Set default encoding - UTF-8");
        src.setDefaultEncoding("UTF-8");

        LOGGER.debug(src.getClass().getName() +": set basename to \"classpath:/static/lang/validation\"");
        src.setBasename("static/lang/validation");

        LOGGER.debug(src.getClass().getName() +" was created.");
        return src;
    }

    @Bean("PasswordProperties")
    public PasswordProperties initPasswordProperties() {
        LOGGER.info("Create " +PasswordProperties.class.getName() +" properties bean.");
        return new PasswordProperties(this.environment);
    }

}
