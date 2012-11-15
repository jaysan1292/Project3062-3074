package com.jaysan1292.project.c3062.util;

import com.jaysan1292.project.c3062.WebAppCommon;
import com.jaysan1292.project.common.data.User;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/** @author Jason Recillo */
public class ServerListener implements ServletContextListener, HttpSessionListener {

    // Public constructor is required by servlet spec
    public ServerListener() {}

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
        /* This method is called when the servlet context is
           initialized(when the Web application is deployed).
           You can initialize servlet context related data here.
        */
        WebAppCommon.initializeApplication();
        sce.getServletContext().setAttribute("aboutLibraries", AboutPageLibrary.LIBRARIES);
    }

    public void contextDestroyed(ServletContextEvent sce) {
        /* This method is invoked when the Servlet Context
           (the Web application) is undeployed or
           Application Server shuts down.
        */
        WebAppCommon.log.info("Servlet context is shutting down.");
        WebAppCommon.shutdownApplication();
    }

    // -------------------------------------------------------
    // HttpSessionListener implementation
    // -------------------------------------------------------
    public void sessionCreated(HttpSessionEvent se) {
        WebAppCommon.log.info("Session created: " + se.getSession().getId());
        se.getSession().setAttribute(WebAppCommon.ATTR_USER, new User());
        se.getSession().setAttribute(WebAppCommon.ATTR_LOGGED_IN, false);
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        WebAppCommon.log.info("Session destroyed: " + se.getSession().getId());
    }
}
