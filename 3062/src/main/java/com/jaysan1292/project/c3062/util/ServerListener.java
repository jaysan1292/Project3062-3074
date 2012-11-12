package com.jaysan1292.project.c3062.util;

import com.jaysan1292.project.c3062.WebAppCommon;
import com.jaysan1292.project.c3062.db.BaseDbManager;
import com.jaysan1292.project.common.data.beans.UserBean;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.lang3.time.StopWatch;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

        StopWatch watch = new StopWatch(); watch.start();

        // Each call of Class.forName(String) here loads the specified class,
        // which in effect will initialize all the static variables in the
        // class, as well as invoke its static constructor.
        WebAppCommon.log.info("Initializing application.");
        try {
            // Create the database
            Class.forName("com.jaysan1292.project.c3062.db.BaseDbManager");

            // Load the placeholder content
            Class.forName("com.jaysan1292.project.c3062.util.PlaceholderContent");

            sce.getServletContext().setAttribute("aboutLibraries", AboutPageLibrary.LIBRARIES);
        } catch (ClassNotFoundException ignored) {}

        watch.stop();
        WebAppCommon.log.info("Initialization complete! Took " + watch.toString());
    }

    public void contextDestroyed(ServletContextEvent sce) {
        /* This method is invoked when the Servlet Context
           (the Web application) is undeployed or
           Application Server shuts down.
        */
        WebAppCommon.log.info("Servlet context is shutting down.");
        Connection conn = null;
        try {
            // Drop the tables
            WebAppCommon.log.info("Dropping tables...");

            // The order here is important;
            String[] drop = {"DROP TABLE comment_t",
                             "DROP TABLE post_t",
                             "DROP TABLE user_t",
                             "DROP TABLE program_t"};

            conn = BaseDbManager.openDatabaseConnection();
            QueryRunner run = new QueryRunner();
            for (String sql : drop) {
                run.update(conn, sql);
            }

            WebAppCommon.log.info("Shutting down database.");
            DriverManager.getConnection("jdbc:derby:" + BaseDbManager.DB_NAME + ";shutdown=true");
        } catch (SQLException e) {
            if ((e.getErrorCode() == 45000) && ("08006".equals(e.getSQLState()))) {
                WebAppCommon.log.info("Database shut down successfully!");
            } else {
                WebAppCommon.log.error("Database did not shut down successfully.", e);
            }
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    // -------------------------------------------------------
    // HttpSessionListener implementation
    // -------------------------------------------------------
    public void sessionCreated(HttpSessionEvent se) {
        WebAppCommon.log.info("Session created: " + se.getSession().getId());
        se.getSession().setAttribute(WebAppCommon.ATTR_USER, new UserBean());
        se.getSession().setAttribute(WebAppCommon.ATTR_LOGGED_IN, false);
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        WebAppCommon.log.info("Session destroyed: " + se.getSession().getId());
    }
}
