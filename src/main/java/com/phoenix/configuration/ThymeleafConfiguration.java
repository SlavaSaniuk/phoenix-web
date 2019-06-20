package com.phoenix.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.ViewResolver;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

/**
 * Thymeleaf framework configuration class. Defined all Thymeleaf/Spring beans (e.g. {@link org.springframework.context.MessageSource}).
 * Class used to set thymeleaf template engine as primary template engine in Spring Web application.
 */
@Configuration
public class ThymeleafConfiguration {

    //LOGGER
    private static final Logger LOGGER = LoggerFactory.getLogger(ThymeleafConfiguration.class);

    //Spring beans
    private ApplicationContext application_context;

    public ThymeleafConfiguration() {
        LOGGER.info("Start to initialize " +getClass().getName() +" configuration class");
    }

    /**
     * Template resolver. Search and return template views under ''prefix' name 'suffix' folder.
     * {@link org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver} is preferred implementation of template resolver.
     * @return - Configured {@link org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver} resolver.
     */
    @Bean(name = "ThymeleafTemplateResolver")
    public SpringResourceTemplateResolver templateResolver() {

        //Default preferred resolver
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();

        //Application context
        resolver.setApplicationContext(this.application_context);
        //Prefix / suffix
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".html");
        //Disable cache
        resolver.setCacheable(false);
        //Encoding
        resolver.setCharacterEncoding("UTF-8");
        //HTML mode
        resolver.setTemplateMode(TemplateMode.HTML);

        //Return
        return resolver;
    }

    /**
     * Template engine. You must configure you template resolvers if that template engine.
     * And add your custom message source to that engine.
     * @return - Configured {@link org.thymeleaf.spring5.SpringTemplateEngine} engine.
     */
    @Bean(name = "ThymeleafTemplateEngine")
    public SpringTemplateEngine templateEngine() {

        //Default template engine
        SpringTemplateEngine engine = new SpringTemplateEngine();

        //Add template resolvers
        //Note if you have more than one templateResolver ->
        //You must enable its in that template engine.
        engine.addTemplateResolver(this.templateResolver());

        //Add message source to template resolver
        engine.setMessageSource(this.createMessageSource());

        //Return
        return engine;
    }

    /**
     * View resolver. Implementation of Spring {@link org.springframework.web.servlet.ViewResolver} interface.
     * View resolver search views by its names, and render them.
     * @return - Configured {@link org.springframework.web.servlet.ViewResolver} resolver.
     */
    @Bean(name = "ThymeleafViewResolver")
    public ViewResolver thymeleafViewResolver() {

        //Create thymeleaf implementation of spring view resolver
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();

        //Set Spring template engine
        resolver.setTemplateEngine(this.templateEngine());

        resolver.setCharacterEncoding("UTF-8");

        //Return
        return resolver;
    }

    /**
     * Create {@link MessageSource} implementation. Message source load internalized messages from property file.
     * This messages used as text in HTML tags.
     * @return - {@link ResourceBundleMessageSource} configured object.
     */
    @Bean(name = "messageSource")
    public MessageSource createMessageSource() {

        ResourceBundleMessageSource msg_src = new ResourceBundleMessageSource();

        //Set parameters
        msg_src.setBasename("static/lang/localization");
        msg_src.setDefaultEncoding("UTF-8");

        return msg_src;
    }

    //Spring autowiring
    @Autowired
    private void autowire(ApplicationContext ctx) {
        this.application_context = ctx;
    }
}
