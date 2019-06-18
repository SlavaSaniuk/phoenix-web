package com.phoenix;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Main Spring web application class (Runner).
 * Class contains {@link Main#onStartup(ServletContext servletContext)} method which created all applications context, servlet context
 * and mapping them to web application context.
 */
public class Main implements WebApplicationInitializer {

    //Logger
    private static Logger LOGGER;

    /**
     * Method called on Spring web application startup.
     * Defined two application (Root, Web) context and register coniguration classes to it's.
     * Map java ee servlet context to web context and create dispatcher servlet.
     * @param servletContext - Application automatic created servlet context.
     * @throws ServletException - If {@link ServletContext} is not created.
     */
    public void onStartup(ServletContext servletContext) throws ServletException {

        //Initialize slf4j logger
        try{
            LOGGER = this.configureLogger();
        }catch (NullPointerException | IOException exc) {
            LOGGER.error(exc.getMessage());
            exc.printStackTrace();
            throw new ServletException(" Application can't initialize logger.");
        }

        //Create root context
        AnnotationConfigWebApplicationContext root_ctx = new AnnotationConfigWebApplicationContext();
        LOGGER.info("Create \'Root\' application context");

        //Set active profiles
        this.setActiveProperties(root_ctx.getEnvironment());
        LOGGER.info("Application active profiles: " + Arrays.toString(root_ctx.getEnvironment().getActiveProfiles()));

        //Register configuration classes
        root_ctx.register(com.phoenix.configuration.RootContextConfiguration.class);
        root_ctx.refresh();

        LOGGER.info("RootContextConfiguration configuration class was registered in root application context");
        //Register in context loader listener
        servletContext.addListener(new ContextLoaderListener(root_ctx));
        LOGGER.info("Root application context was created");

        //Create web application context
        AnnotationConfigWebApplicationContext web_ctx = new AnnotationConfigWebApplicationContext();
        LOGGER.info("Create \'Web\' application context");
        //Set servlet context to this context
        web_ctx.setServletContext(servletContext);
        //Set parent context
        web_ctx.setParent(root_ctx);
        web_ctx.refresh();
        LOGGER.info("Web application context set as child to Root application context");
        //Register configuration classes
        web_ctx.register(com.phoenix.configuration.WebContextConfiguration.class);
        web_ctx.register(com.phoenix.configuration.ThymeleafConfiguration.class);
        web_ctx.refresh();
        LOGGER.info("WebContextConfiguration configuration class was registered in root application context");
        LOGGER.info("ThymeleafConfiguration configuration class was registered in root application context");
        LOGGER.info("Web application context was created");

        //Register dispatcher servlet
        ServletRegistration.Dynamic dispatcher_servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(web_ctx));
        dispatcher_servlet.addMapping("/");
        dispatcher_servlet.setLoadOnStartup(1);
        LOGGER.info("Dispatcher servlet was created and registered");

    }

    /*
        Read log4j configuration file from "resources/configuration-files/" directory and
        configure log4j logger framework to use appenders defined in them.
        Return configured logger to main class.
     */
    private Logger configureLogger() throws NullPointerException, IOException {

        //Get "log4j" configuration file
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("configuration-files" + File.separator +"log4j.properties");

        if (in == null) throw new NullPointerException("/'log4j.properties/' configuration file is not found under resource/configuration-files/ directory");

        //Configure log4j
        PropertyConfigurator.configure(in);

        //Close input stream
        try{
            in.close();
        }catch (IOException exc) {
            exc.printStackTrace();
            throw new IOException("Can't to close InputStream to \"log4j.properties\" configuration file");
        }

        return LoggerFactory.getLogger(this.getClass());
    }

    private void setActiveProperties(ConfigurableEnvironment env) {

        //Available profiles
        List<String> available_profiles = new ArrayList<>();
        available_profiles.add("DEVELOPMENT");
        available_profiles.add("PRODUCTION");
        available_profiles.add("TEST");

        //Get application configuration file
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("configuration-files" + File.separator +"log4j.properties");
        if (in == null) {
            LOGGER.warn("Can't to find \"application.properties\" file under resource/configuration-files/ directory ");
            LOGGER.warn("Set application active profile to default value.");
            env.setActiveProfiles(available_profiles.get(0));
            return;
        }

        //Create configuration properties
        Properties application_properties = new Properties();
        try {
            application_properties.load(in); //Load properties from file
            in.close(); //Close input stream
        } catch (IOException exc) {
            LOGGER.warn(exc.getMessage());
            LOGGER.warn("Set application active profile to default value.");
            env.setActiveProfiles(available_profiles.get(0));
            return;
        }

        //Set active profile
        String active_profile = application_properties.getProperty("com.phoenix.application.active_profile");

        //Assert active profile is set in incorrect or null value
        if (active_profile == null || !available_profiles.contains(active_profile.toUpperCase())) {
            LOGGER.warn("Property \'com.phoenix.application.active_profile\' is not set or set is in incorrect value. Application will use default (DEVELOPMENT) profile.");
            env.setActiveProfiles(available_profiles.get(0));
        }else {
            LOGGER.info("Set application active profile to " +active_profile +" value.");
            env.setActiveProfiles(active_profile);
        }

    }
}
