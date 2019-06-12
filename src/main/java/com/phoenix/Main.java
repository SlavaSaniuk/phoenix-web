package com.phoenix;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class Main implements WebApplicationInitializer {

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
