package com.jaysan1292.project.c3062.servlets;

import com.jaysan1292.project.c3062.WebAppCommon;
import com.jaysan1292.project.c3062.db.PostDbManager;
import com.jaysan1292.project.common.data.Post;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(WebAppCommon.SRV_POST)
public class PostDetailServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WebAppCommon.log.debug("PostDetailServlet POST");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WebAppCommon.log.debug("PostDetailServlet GET");
        if (WebAppCommon.checkLoginAndAuthenticate(request, response)) {
            if (request.getParameter("id") != null) {
                int postId = NumberUtils.toInt(request.getParameter("id"));
                Post post = PostDbManager.getSharedInstance().getPost(postId);

                if (post != null) {
                    request.setAttribute("post", post);
                    request.getRequestDispatcher(WebAppCommon.JSP_POST).forward(request, response);
                } else {
                    request.getRequestDispatcher("/404.jsp").forward(request, response);
                }
            } else {
                request.getRequestDispatcher("/404.jsp").forward(request, response);
            }
        }
    }
}
