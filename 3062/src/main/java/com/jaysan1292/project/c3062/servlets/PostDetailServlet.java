package com.jaysan1292.project.c3062.servlets;

import com.jaysan1292.project.c3062.WebAppCommon;
import com.jaysan1292.project.c3062.data.beans.PostBean;
import com.jaysan1292.project.c3062.db.CommentDbManager;
import com.jaysan1292.project.c3062.db.PostDbManager;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

@WebServlet(WebAppCommon.SRV_POST)
public class PostDetailServlet extends HttpServlet {

//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        WebAppCommon.log.debug("PostDetailServlet POST");
//    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WebAppCommon.log.debug("PostDetailServlet GET");
        if (WebAppCommon.checkLoginAndAuthenticate(request, response)) {
            response.setCharacterEncoding("UTF-8");

            if (request.getParameter("id") != null) {
                try {
                    int postId = NumberUtils.toInt(request.getParameter("id"));
                    PostBean post = new PostBean();
                    post.setPost(PostDbManager.getSharedInstance().get(postId));
                    post.setComments(Arrays.asList(CommentDbManager.getSharedInstance().getComments(post.getPost())));

                    if (post.getPost() != null) {
                        request.setAttribute("post", post);
                        request.getRequestDispatcher(WebAppCommon.JSP_POST).forward(request, response);
                    } else {
                        request.getRequestDispatcher("/404.jsp").forward(request, response);
                    }
                } catch (SQLException e) {
                    throw new ServletException(e);
                }
            } else {
                request.getRequestDispatcher("/404.jsp").forward(request, response);
            }
        }
    }
}
