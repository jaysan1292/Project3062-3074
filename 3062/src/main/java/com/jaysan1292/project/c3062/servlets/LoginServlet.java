package com.jaysan1292.project.c3062.servlets;

import com.jaysan1292.project.c3062.WebAppCommon;
import com.jaysan1292.project.c3062.db.ProgramDbManager;
import com.jaysan1292.project.c3062.db.UserManager;
import com.jaysan1292.project.common.data.Program;
import com.jaysan1292.project.common.data.beans.UserBean;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@WebServlet(WebAppCommon.SRV_LOGIN)
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WebAppCommon.log.debug("LoginServlet POST");
        //process the credentials
        HttpSession session = request.getSession();

        String username = request.getParameter("login_username");
        String password = request.getParameter("login_password");

        UserBean user = new UserBean();
        try {
            user.setUser(UserManager.getSharedInstance().getUser(username));
            user.setPassword(UserManager.getSharedInstance().getPassword(user.getUser()));
        } catch (SQLException e) {
            throw new ServletException(e);
        }

//        User user = UserDbManager.getSharedInstance().getUserByStudentId(username);

        if ((user.getUser() == null) || !user.getPassword().comparePassword(password)) {
            // The given user was not found, or the password was
            // incorrect, so send the user back to the login page
            request.setAttribute("errorMessage", "Username or password was incorrect.");
            doGet(request, response);
            return;
        }

        setSessionUserLoggedIn(session, user);

        String sendBackUrl = WebAppCommon.REGEX_JSP.matcher(String.valueOf(session.getAttribute("sendBackUrl"))).replaceAll("");

        if (StringUtils.isBlank(sendBackUrl) || StringUtils.equals(sendBackUrl, "null"))
            sendBackUrl = request.getContextPath() + "/feed";

        WebAppCommon.log.debug("Redirecting to: " + response.encodeRedirectURL(sendBackUrl));

        response.sendRedirect(response.encodeRedirectURL(sendBackUrl));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WebAppCommon.log.debug("LoginServlet GET");
        //return the login page
        boolean shouldLogout = BooleanUtils.toBoolean(NumberUtils.toInt(request.getParameter("logout")));

        HttpSession session = request.getSession();
        if (shouldLogout) {
            setSessionUserLoggedOut(request, response);
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + '/'));
        } else {
            session.setAttribute("sendBackUrl", request.getParameter("sendBackUrl"));
            List<Program> programs = Arrays.asList(ProgramDbManager.getSharedInstance().getAll());
//            ArrayList<Program> programs = new ArrayList<Program>(Program.AllPrograms.values());

            Collections.sort(programs);

            request.setAttribute("programs", programs);

            if (request.getAttribute("errorMessage") == null) {
                request.setAttribute("errorMessage", "");
            }
            request.getRequestDispatcher(WebAppCommon.JSP_LOGIN).include(request, response);
        }
    }

    private static void setSessionUserLoggedIn(HttpSession session, UserBean user) {
        session.setAttribute(WebAppCommon.ATTR_LOGGED_IN, true);
        session.setAttribute(WebAppCommon.ATTR_USER, user);

        WebAppCommon.log.info(user.getFullName() + " has logged in.");
    }

    private static void setSessionUserLoggedOut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        WebAppCommon.log.info(((UserBean) session.getAttribute(WebAppCommon.ATTR_USER)).getFullName() + " has logged out.");
        session.setAttribute(WebAppCommon.ATTR_LOGGED_IN, false);
        session.setAttribute(WebAppCommon.ATTR_USER, new UserBean());
        session.removeAttribute(WebAppCommon.ATTR_USER_FEED);
    }
}
