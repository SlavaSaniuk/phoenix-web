package com.phoenix;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

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

        //Create root context
        AnnotationConfigWebApplicationContext root_ctx = new AnnotationConfigWebApplicationContext();
        //Register configuration classes
        root_ctx.register(com.phoenix.configuration.RootContextConfiguration.class);
        root_ctx.refresh();
        //Register in context loader listener
        servletContext.addListener(new ContextLoaderListener(root_ctx));

        //Create web application context
        AnnotationConfigWebApplicationContext web_ctx = new AnnotationConfigWebApplicationContext();
        //Set servlet context to this context
        web_ctx.setServletContext(servletContext);
        //Set parent context
        web_ctx.setParent(root_ctx);
        web_ctx.refresh();
        //Register configuration classes
        web_ctx.register(com.phoenix.configuration.WebContextConfiguration.class);
        web_ctx.register(com.phoenix.configuration.ThymeleafConfiguration.class);
        web_ctx.refresh();

        //Register dispatcher servlet
        ServletRegistration.Dynamic dispatcher_servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(web_ctx));
        dispatcher_servlet.addMapping("/");
        dispatcher_servlet.setLoadOnStartup(1);

    }
}
