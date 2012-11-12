package com.jaysan1292.project.c3062.servlets;

import com.jaysan1292.project.c3062.WebAppCommon;
import com.jaysan1292.project.c3062.db.PostDbManager;
import com.jaysan1292.project.c3062.db.UserDbManager;
import com.jaysan1292.project.common.data.Post;
import com.jaysan1292.project.common.data.User;
import com.jaysan1292.project.common.data.beans.ProfileBean;
import com.jaysan1292.project.common.data.beans.UserBean;
import com.jaysan1292.project.common.util.SortedArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.jaysan1292.project.c3062.WebAppCommon.ATTR_USER;

@WebServlet(WebAppCommon.SRV_PROFILE)
public class ProfileServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WebAppCommon.log.debug("ProfileServlet GET");

        if (WebAppCommon.checkLoginAndAuthenticate(request, response)) {
            Long requestedUserId;
            try {
                requestedUserId = Long.parseLong(request.getParameter("id"));
            } catch (NumberFormatException e) {
                requestedUserId = ((UserBean) request.getSession().getAttribute(ATTR_USER)).getUser().getId();
            }

            WebAppCommon.log.debug("Requested user profile ID: " + requestedUserId);

            User user;
            SortedArrayList<Post> posts;
            user = UserDbManager.getSharedInstance().getUser(requestedUserId);
            posts = PostDbManager.getSharedInstance().getPosts(user);

            if (user != null) {
                ProfileBean profile = new ProfileBean();
                profile.setUser(user);
                profile.setPosts(posts);
                request.setAttribute("profile", profile);
                request.getRequestDispatcher(WebAppCommon.JSP_PROFILE).include(request, response);
            } else {
                request.getRequestDispatcher("/404").forward(request, response);
            }
        }
    }
}
