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
import java.util.*;

/**
 * Main Spring web application class (Runner).
 * Class contains {@link Main#onStartup(ServletContext servletContext)} method which created all applications context, servlet context
 * and mapping them to web application context.
 */
public class Main implements WebApplicationInitializer {

    private static Logger LOGGER; //Logger
    /**
     * Main configuration applications properties.
     * Use APPLICATION_PROPERTIES.getProperty method
     * to get configuration property.
     */
    private static Properties APPLICATION_PROPERTIES;

    /**
     * Constructor configure application logger framework (slf4j) {@link Main#LOGGER} and
     * initialize application configuration properties {@link Main#APPLICATION_PROPERTIES}.
     */
    public Main() {
        try{
            LOGGER = this.configureLogger();
            APPLICATION_PROPERTIES = this.initApplicationProperties();
        }catch (IOException exc) {
            if (LOGGER != null) {
                LOGGER.error(exc.getMessage(), exc);
            }else {
                System.out.println(exc.getMessage());
                exc.printStackTrace();
            }
            System.exit(1);
        }
    }

    /**
     * Method called on Spring web application startup.
     * Defined two application (Root, Web) context and register coniguration classes to it's.
     * Map java ee servlet context to web context and create dispatcher servlet.
     * @param servletContext - Application automatic created servlet context.
     * @throws ServletException - If {@link ServletContext} is not created.
     */
    public void onStartup(ServletContext servletContext) throws ServletException {

         //Create root context
        LOGGER.info("Create \'Root\' application context");
        AnnotationConfigWebApplicationContext root_ctx = new AnnotationConfigWebApplicationContext();

        //Set active profiles
        this.setActiveProperties(APPLICATION_PROPERTIES, root_ctx.getEnvironment());

        //Register configuration classes
        root_ctx.register(com.phoenix.configuration.RootContextConfiguration.class);
        LOGGER.info("RootContextConfiguration configuration class was registered in root application context");

        //Register context in context loader listener
        LOGGER.info("Register Root application context in ContextLoaderListener");
        servletContext.addListener(new ContextLoaderListener(root_ctx));
        LOGGER.info("Root application context was created");


        //Create web application context
        LOGGER.info("Create \'Web\' application context");
        AnnotationConfigWebApplicationContext web_ctx = new AnnotationConfigWebApplicationContext();

        //Register configuration classes
        web_ctx.register(com.phoenix.configuration.WebContextConfiguration.class);
        LOGGER.info("WebContextConfiguration configuration class was registered in web application context");
        web_ctx.register(com.phoenix.configuration.ThymeleafConfiguration.class);
        LOGGER.info("ThymeleafConfiguration configuration class was registered in web application context");

        //Register dispatcher servlet
        ServletRegistration.Dynamic dispatcher_servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(web_ctx));
        dispatcher_servlet.addMapping("/");
        dispatcher_servlet.setLoadOnStartup(1);
        LOGGER.info("Dispatcher servlet was created and registered");

        LOGGER.info("Web application context was created");
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

    /*
        Method read configuration properties from application.properties file under
        "resource/configuration-files" directory. Configuration property from this properties
        object used to to set as properties values in Spring configurations methods.
     */
    private Properties initApplicationProperties() throws NullPointerException, IOException {

        Properties props = new Properties();

        //Get application.properties configuration file
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("configuration-files" + File.separator +"application.properties");
        if (in == null) throw new NullPointerException();

        LOGGER.info("Load application configuration properties from application.properties file.");
        //Load properties from file
        props.load(in);

        //Close input stream
        in.close();

        //Assert that configuration file is empty
        if (props.isEmpty()) {
            LOGGER.warn("Application configuration file is empty");
            return props;
        }

        //Log all not empty loaded  properties
        Set<Map.Entry<Object, Object>> entries = props.entrySet();
        LOGGER.debug("All application configuration properties: ");
        for (Map.Entry<Object, Object> entry : entries) {
            if (entry.getValue().toString().equals("")) continue;
            LOGGER.debug("KEY: " + entry.getKey().toString());
            LOGGER.debug("VALUE: " + entry.getValue().toString());
            LOGGER.debug("");
        }

        //Return
        return props;
    }

    /*
        Set active profiles values to Spring Environment of Root application context.
        Active profiles contains in application.properties file with key "com.phoenix.application.active_profiles".
     */
    private void setActiveProperties(Properties a_application_properties, ConfigurableEnvironment env) {

        //Available profiles
        List<String> available_profiles = new ArrayList<>();
        available_profiles.add("DEVELOPMENT");
        available_profiles.add("PRODUCTION");
        available_profiles.add("TEST");

        //Get active profile from properties
        String active_profile = a_application_properties.getProperty("com.phoenix.application.active_profile");

        //Assert if "com.phoenix.application.active_profile" property is not found in properties
        if (active_profile == null) {
            LOGGER.warn("Property \"com.phoenix.application.active_profile\" is not found in properties file. Application will use default (DEVELOPMENT) profile.");
            env.setActiveProfiles(available_profiles.get(0));
            return;
        }

        //TO UPPER CASE
        active_profile = active_profile.toUpperCase();

        //Assert if "com.phoenix.application.active_profile" property is not set or set with invalid value
        if (active_profile.equals("") || !available_profiles.contains(active_profile)) {
            LOGGER.warn("Property \"com.phoenix.application.active_profile\" is not set or set with invalid value. Application will use default (DEVELOPMENT) profile.");
            env.setActiveProfiles(available_profiles.get(0));
            return;
        }

        //Then
        env.setActiveProfiles(active_profile);
        LOGGER.info("Application active profile: " + Arrays.toString(env.getActiveProfiles()));

    }


}
