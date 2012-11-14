package com.jaysan1292.project.c3062;

import com.jaysan1292.project.c3062.db.DatabaseInitializer;
import com.jaysan1292.project.common.data.User;
import com.jaysan1292.project.common.data.beans.UserBean;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public abstract class WebAppCommon {
    public static final Logger log = Logger.getLogger(WebAppCommon.class);
    public static final String AppName = "gbc.myCommunity";
    public static final String AppTagline = "Connect, share, and learn";
    public static final ArrayList<String[]> HeaderMenu = new ArrayList<String[]>() {{
        add(new String[]{"Home", "/", "icon-home"});
        add(new String[]{"Feed", "/feed", "icon-th-list"});
        add(new String[]{"Profile", "/profile", "icon-user"});
    }};

    //session/cookie attributes
    /** (Boolean) Whether or not the user is logged in. */

    public static final String ATTR_LOGGED_IN = "loggedIn";

    /** (UserBean) The user object containing the information of the currently logged in user. */
    public static final String ATTR_USER = "user";

    /** (FeedBean) The bean class containing the user's home feed. */
    public static final String ATTR_USER_FEED = "userFeed";

    // servlet mappings
    public static final String SRV_LOGIN = "/login";
    public static final String SRV_PROFILE = "/profile";
    public static final String SRV_POST = "/post";
    public static final String SRV_FEED = "/feed";
    public static final String SRV_CSS = "/css/application.css";
    public static final String SRV_POSTCOMMENT = "/post/comment";
    public static final String SRV_ABOUT = "/about";
    public static final String SRV_REGISTER = "/register";

    // jsp pages
    private static final String JSP_ROOT = "/WEB-INF/jsp";
    public static final String JSP_ABOUT = JSP_ROOT + "/about.jsp";
    public static final String JSP_FEED = JSP_ROOT + "/feed.jsp";
    public static final String JSP_LOGIN = JSP_ROOT + "/login.jsp";
    public static final String JSP_POST = JSP_ROOT + "/post.jsp";
    public static final String JSP_PROFILE = JSP_ROOT + "/profile.jsp";

    // regex
    public static final Pattern REGEX_JSP = Pattern.compile("(.*)/WEB-INF/jsp(.*)(\\.jsp)");
    private static final Pattern QUERY_STRING_AMP = Pattern.compile("&");
    private static final Pattern QUERY_STRING_VALUE = Pattern.compile("=");

    ///////////////////////////////////////////////////////////////////////////

    public static boolean checkLoginAndAuthenticate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        boolean loggedIn = getLoginStatus(session);

        if (!loggedIn) {
            //Redirect to login page and then send them back to where they were trying to go before
            WebAppCommon.log.trace("Redirecting user to login page.");
            response.sendRedirect(request.getServletContext().getContextPath() +
                                  SRV_LOGIN + '?' + "sendBackUrl=" +
                                  response.encodeRedirectURL(
                                          request.getRequestURI() +
                                          ((request.getQueryString() == null) ? "" : ('?' + request.getQueryString()))));
            return false;
        }
        return true;
    }

    public static boolean getLoginStatus(HttpSession session) {
        Boolean loggedIn = (Boolean) session.getAttribute(ATTR_LOGGED_IN);
        return (loggedIn == null) ? false : loggedIn;
    }

    public static User getLoggedInUser(HttpSession session) {
        return ((UserBean) session.getAttribute(ATTR_USER)).getUser();
    }

    public static Map<String, String> queryStringToMap(HttpServletRequest request) {
        // If there is no query string, return an empty Map
        String queryString = request.getQueryString();
        if ((queryString == null) || queryString.isEmpty()) return new HashMap<String, String>();

        final String[] queryParams = QUERY_STRING_AMP.split(queryString);

        return new HashMap<String, String>() {{
            for (String param : queryParams) {
                String[] value = QUERY_STRING_VALUE.split(param);
                put(value[0], value[1]);
            }
        }};
    }

    ///////////////////////////////////////////////////////////////////////////
    private static boolean _initialized;

    public static void initializeApplication() {
        assert !_initialized;

        StopWatch watch = new StopWatch(); watch.start();

        // Each call of Class.forName(String) here loads the specified class,
        // which in effect will initialize all the static variables in the
        // class, as well as invoke its static constructor.
        WebAppCommon.log.info("Initializing application.");

        WebAppCommon.log.info("Current working directory is: " + System.getProperty("user.dir"));
        try {
            // Create the database
            DatabaseInitializer.initialize();

            // Load the placeholder content
            Class.forName("com.jaysan1292.project.c3062.util.PlaceholderContent");
        } catch (ClassNotFoundException ignored) {}

        watch.stop();
        WebAppCommon.log.info("Initialization complete! Took " + watch.toString());

        _initialized = true;
    }

    public static void shutdownApplication() {
        assert _initialized;

        DatabaseInitializer.shutdown();

        _initialized = false;
    }
}
