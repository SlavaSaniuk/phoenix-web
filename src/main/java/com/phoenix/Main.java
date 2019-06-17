package com.phoenix;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * Main Spring web application class (Runner).
 * Class contains {@link Main#onStartup(ServletContext servletContext)} method which created all applications context, servlet context
 * and mapping them to web application context.
 */
public class Main implements WebApplicationInitializer {

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
            this.configureLogger();
        }catch (IOException exc) {
            exc.printStackTrace();
            throw new ServletException(" Application can't initialize logger.");
        }

        //Create logger
        Logger logger = LoggerFactory.getLogger(Main.class);

        //Create root context
        AnnotationConfigWebApplicationContext root_ctx = new AnnotationConfigWebApplicationContext();
        logger.info("Create \'Root\' application context");
        //Register configuration classes
        root_ctx.register(com.phoenix.configuration.RootContextConfiguration.class);
        root_ctx.refresh();
        logger.info("RootContextConfiguration configuration class was registered in root application context");
        //Register in context loader listener
        servletContext.addListener(new ContextLoaderListener(root_ctx));
        logger.info("Root application context was created");

        //Create web application context
        AnnotationConfigWebApplicationContext web_ctx = new AnnotationConfigWebApplicationContext();
        logger.info("Create \'Web\' application context");
        //Set servlet context to this context
        web_ctx.setServletContext(servletContext);
        //Set parent context
        web_ctx.setParent(root_ctx);
        web_ctx.refresh();
        logger.info("Web application context set as child to Root application context");
        //Register configuration classes
        web_ctx.register(com.phoenix.configuration.WebContextConfiguration.class);
        web_ctx.register(com.phoenix.configuration.ThymeleafConfiguration.class);
        web_ctx.refresh();
        logger.info("WebContextConfiguration configuration class was registered in root application context");
        logger.info("ThymeleafConfiguration configuration class was registered in root application context");
        logger.info("Web application context was created");

        //Register dispatcher servlet
        ServletRegistration.Dynamic dispatcher_servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(web_ctx));
        dispatcher_servlet.addMapping("/");
        dispatcher_servlet.setLoadOnStartup(1);
        logger.info("Dispatcher servlet was created and registered");

    }

    /*
        Read log4j configuration file from "resources/configuration-files/" directory and
        configure log4j logger framework to use appenders defined in them.
     */
    private void configureLogger() throws IOException {

        //Get "log4j" configuration file
        InputStream in = this.getClass().getClassLoader().getResourceAsStream("configuration-files" + File.separator +"log4j.properties");

        if (in == null) throw new IOException("/'log4j.properties/' configuration file is not found under resource/configuration-files/ directory");

        //Configure log4j
        PropertyConfigurator.configure(in);

        //Close input stream
        in.close();

    }
}
