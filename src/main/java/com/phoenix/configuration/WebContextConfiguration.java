package com.phoenix.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * Spring web application web context main configuration class (Marked with {@link Configuration} annotation).
 * WebContextConfiguration class implements {@link WebMvcConfigurer} interface and override its default methods for
 * configuring web context.
 * {@link ThymeleafConfiguration} class used to configure Thymeleaf template engine but for cleaning code and clarity
 * of understanding we manually register it in {@link org.thymeleaf.context.WebContext} application web context
 * after {@link WebContextConfiguration} class.
 */
@Configuration
@EnableWebMvc
@ComponentScan( basePackageClasses = WebContextConfiguration.class, basePackages = "com.phoenix.controllers")
public class WebContextConfiguration implements WebMvcConfigurer {

    //LOGGER
    private static final Logger LOGGER = LoggerFactory.getLogger(WebContextConfiguration.class);

    public WebContextConfiguration() {
        LOGGER.info("Start to initialize " +getClass().getName() +" configuration class");
    }

    /**
     * Configure a handler to delegate unhandled requests by forwarding to the
     * Servlet container's "default" servlet. A common use case for this is when
     * the {@link DispatcherServlet} is mapped to "/" thus overriding the
     * Servlet container's default handling of static resources.
     */
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * Add handlers to serve static resources such as images, js, and, css
     * files from specific locations under web application root, the classpath,
     * and others.
     */
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //Add styles to views as static resource
        registry.addResourceHandler("/styles/**").addResourceLocations("classpath:static/style/");
        //Add javascript libraries to view
        registry.addResourceHandler("/libs/**").addResourceLocations("classpath:static/lib/");
        //Add images to view as static resources
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:static/img/");
        //Add localization messages files to thymeleaf message source
        registry.addResourceHandler("/lang/**").addResourceLocations("classpath:static/lang/");
    }

    /**
     * Configure simple automated controllers pre-configured with the response
     * status code and/or a view to render the response body. This is useful in
     * cases where there is no need for custom controller logic -- e.g. render a
     * home page, perform simple site URL redirects, return a 404 status with
     * HTML content, a 204 with no content, and more.
     */
    public void addViewControllers(ViewControllerRegistry registry) {
        //Redirect from root to view page
       registry.addRedirectViewController("/", "/login");
       registry.addRedirectViewController("/index", "/login");
    }

    /**
     * Method add custom user defined interceptors to spring web engine.
     * @param registry - registry of interceptors.
     */
    public void addInterceptors(InterceptorRegistry registry) {
        //Add locales change interceptor
        registry.addInterceptor(this.createLocaleChangeInterceptor());
    }

    /**
     * Spring interceptor which used to change user request locale.
     * Interceptor catch all user HTTP request and look for "Change-Locale" parameter.
     * If parameter is set, interceptor set parameter value to user session and the next web page
     * will be translated to parameter value language. If parameter value is unknown or not supported
     * Spring will be use a default locale defined in {@link org.springframework.web.servlet.LocaleResolver} bean.
     * @return - {@link LocaleChangeInterceptor} configured bean.
     */
    @Bean(name = "localeChangeInterceptor")
    public LocaleChangeInterceptor createLocaleChangeInterceptor() {

        LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();

        //Set change localization trigger
        interceptor.setParamName("Change-Locale");

        return interceptor;
    }

    /**
     * Spring LocaleResolver bean used to resolve user locale.
     * Default locale can be change by using a {@link LocaleChangeInterceptor} bean.
     * @return - {@link SessionLocaleResolver} configured bean, which hold user locale in session attribute.
     */
    @Bean(name = "localeResolver")
    public LocaleResolver createLocaleResolver() {

        SessionLocaleResolver resolver = new SessionLocaleResolver();

        //Set default locale (US)
        resolver.setDefaultLocale(Locale.ENGLISH);

        return resolver;
    }
}
