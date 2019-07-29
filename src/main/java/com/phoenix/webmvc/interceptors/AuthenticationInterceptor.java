package com.phoenix.webmvc.interceptors;

import com.phoenix.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthenticationInterceptor implements HandlerInterceptor {

    //Logger
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        LOGGER.info( request.getRequestedSessionId() +": access to " +request.getRequestURI());
        //Get user session
        HttpSession session = request.getSession(false);

        //Check whether session is created
        if(session == null) {
            LOGGER.debug(request.getRequestedSessionId() +": session is not created.");
            //Redirect to login page
            response.sendRedirect(request.getContextPath() +"/login");
            return false;
        }

        //Check whether user is authenticated
        Boolean authneticated = (Boolean) session.getAttribute("authenticated");
        if (authneticated == null ||! authneticated) {
            LOGGER.debug(request.getRequestedSessionId() +": authenticated: " +authneticated);
            //Redirect to login page
            response.sendRedirect(request.getContextPath() +"/login");
            return false;
        }else {
            User user =  (User) session.getAttribute("current_user");
            LOGGER.debug(request.getRequestedSessionId() +" authenticated user (" +user.getUserId() +");");
            return true;
        }
    }
}
