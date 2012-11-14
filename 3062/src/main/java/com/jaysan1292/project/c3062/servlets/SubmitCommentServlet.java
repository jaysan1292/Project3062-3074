package com.jaysan1292.project.c3062.servlets;

import com.jaysan1292.project.c3062.WebAppCommon;
import com.jaysan1292.project.c3062.db.CommentDbManager;
import com.jaysan1292.project.c3062.db.UserDbManager;
import com.jaysan1292.project.common.data.Comment;
import com.jaysan1292.project.common.data.User;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/** @author Jason Recillo */
@WebServlet(WebAppCommon.SRV_POSTCOMMENT)
public class SubmitCommentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (WebAppCommon.checkLoginAndAuthenticate(request, response)) {
            WebAppCommon.log.info("SubmitCommentServlet POST");
            Map<String, String[]> params = request.getParameterMap();

            long userId = Long.parseLong(params.get("comment-poster")[0]);
            User commentAuthor;
            try {
                commentAuthor = UserDbManager.getSharedInstance().get(userId);
            } catch (SQLException e) {
                throw new ServletException(e);
            }
            String commentBody = params.get("comment")[0];
            Date commentDate = new Date(Long.parseLong(params.get("comment-date")[0]));
            long parentPostId = Long.parseLong(params.get("post-id")[0]);

            Comment comment = new Comment();
            comment.setCommentAuthor(commentAuthor);
            comment.setCommentBody(commentBody);
            comment.setCommentDate(commentDate);
            comment.setParentPostId(parentPostId);

            try {
                CommentDbManager.getSharedInstance().insert(comment);
            } catch (SQLException e) {
                throw new ServletException(e);
            }

            //Dynamically create a new JSP file to generate the required HTML for the comment
            File root = new File(getServletContext().getRealPath("/"));
            String filename = UUID.randomUUID() + "_comment.jsp";
            String jsp = "<%@ taglib prefix=\"t\" tagdir=\"/WEB-INF/tags\" %>" +
                         "<jsp:useBean id=\"comment\" scope=\"request\" type=\"com.jaysan1292.project.common.data.Comment\"/>" +
                         "<t:post_comment comment=\"${comment}\"/>";
            File commentJsp = new File(root, filename);
            write(jsp, commentJsp);

            request.setAttribute("comment", comment);
            request.getRequestDispatcher('/' + filename).forward(request, response);
            commentJsp.delete();

            WebAppCommon.log.info("A comment has been posted to post #" + parentPostId);
        }
    }

    @SuppressWarnings("IOResourceOpenedButNotSafelyClosed")
    private static void write(String content, File file) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));
            writer.write(content);
        } catch (IOException e) {
            WebAppCommon.log.error(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(writer);
        }
    }
}
