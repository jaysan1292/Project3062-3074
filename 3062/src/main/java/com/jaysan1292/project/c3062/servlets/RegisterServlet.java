package com.jaysan1292.project.c3062.servlets;

import com.jaysan1292.project.c3062.WebAppCommon;
import com.jaysan1292.project.c3062.db.ProgramDbManager;
import com.jaysan1292.project.c3062.db.UserDbManager;
import com.jaysan1292.project.common.data.Program;
import com.jaysan1292.project.common.data.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.Map;

/** @author Jason Recillo */
@WebServlet(WebAppCommon.SRV_REGISTER)
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WebAppCommon.log.debug("RegisterServlet POST");
        request.setCharacterEncoding("UTF-8");

        Map<String, String[]> params = request.getParameterMap();
        try {
            String firstName = WordUtils.capitalizeFully(params.get("firstName")[0]);
            String lastName = WordUtils.capitalizeFully(params.get("lastName")[0]);
            Program program = ProgramDbManager.getSharedInstance().getProgram(params.get("program")[0]);
            String studentNumber = params.get("studentNumber")[0];
            String email = params.get("email")[0];
            String password = params.get("password")[0];

            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setProgram(program);
            user.setStudentNumber(studentNumber);
            user.setEmail(email);
            user.setPassword(password);

            // Add this new user into the database
            WebAppCommon.log.debug("Registering new user " + user.getFullName());
            UserDbManager.getSharedInstance().insert(user);

            // now log the new user in and redirect them to the homepage
            LoginServlet.setSessionUserLoggedIn(request.getSession(), UserDbManager.getSharedInstance().getUser(studentNumber));
            response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath() + WebAppCommon.SRV_FEED));
        } catch (SQLException e) {
            WebAppCommon.log.error("Error processing form!", e);
            throw new ServletException(e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WebAppCommon.log.debug("RegisterServlet GET");

        Map<String, String> params = WebAppCommon.queryStringToMap(request);
        // if a parameter is null, then just set it to blank
        String studentNumber = StringUtils.defaultString(params.get("studentNumber"));
        String email = URLDecoder.decode(StringUtils.defaultString(params.get("email")), "UTF-8");
        if (StringUtils.isNotBlank(studentNumber)) {
            WebAppCommon.log.debug("Checking " + studentNumber + " for uniqueness");
            boolean unique = UserDbManager.getSharedInstance().isStudentNumberUnique(studentNumber);
            response.setContentType(MediaType.APPLICATION_JSON);

            if (unique) WebAppCommon.log.debug(studentNumber + " is valid!");
            else WebAppCommon.log.debug(studentNumber + " is not valid.");

            response.getWriter().write(String.valueOf(unique));
        } else if (StringUtils.isNotBlank(email)) {
            WebAppCommon.log.debug("Checking " + email + " for uniqueness");
            boolean unique = UserDbManager.getSharedInstance().isEmailUnique(email);
            response.setContentType(MediaType.APPLICATION_JSON);

            if (unique) WebAppCommon.log.debug(email + " is valid!");
            else WebAppCommon.log.debug(email + " is not valid.");

            response.getWriter().write(String.valueOf(unique));
        } else {
            request.getRequestDispatcher(WebAppCommon.JSP_LOGIN).forward(request, response);
        }
    }
}
