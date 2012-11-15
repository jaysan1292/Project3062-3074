package com.jaysan1292.project.c3062.servlets;

import com.jaysan1292.project.c3062.WebAppCommon;
import com.jaysan1292.project.c3062.db.PostDbManager;
import com.jaysan1292.project.common.data.Post;
import com.jaysan1292.project.common.data.User;
import com.jaysan1292.project.common.data.beans.FeedBean;
import com.jaysan1292.project.common.data.beans.PostBean;
import com.jaysan1292.project.common.util.SortedArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

import static com.jaysan1292.project.c3062.WebAppCommon.ATTR_USER_FEED;

@WebServlet(WebAppCommon.SRV_FEED)
public class FeedServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WebAppCommon.log.debug("FeedServlet POST");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WebAppCommon.log.debug("FeedServlet GET");

        if (WebAppCommon.checkLoginAndAuthenticate(request, response)) {
            HttpSession session = request.getSession();
            User user = WebAppCommon.getLoggedInUser(session);

            Map<String, String> queryMap = WebAppCommon.queryStringToMap(request);

            int index;
            try {
                index = Integer.parseInt(queryMap.get("order"));
            } catch (NumberFormatException e) {
                index = 0;
            }

            FeedBean feed = new FeedBean();
            feed.setUser(user);
            feed.setPosts(getUserFeedPosts(user));
            feed.setOffset(index);

            WebAppCommon.log.debug(String.format("There are %d posts on %s's feed, and %d posts visible on this page.",
                                                 feed.getPostCount(),
                                                 feed.getUser().getFullName(),
                                                 feed.getPostsWithOffset().size()));

            request.setAttribute(ATTR_USER_FEED, feed);
            request.getRequestDispatcher(WebAppCommon.JSP_FEED).forward(request, response);
        }
    }

    private static SortedArrayList<PostBean> getUserFeedPosts(User user) {
//        //TODO: Do this better ;p
//        SortedArrayList<PostBean> feed = new SortedArrayList<PostBean>();
//        CollectionUtils.select(
//                PostDbManager.getSharedInstance().getAllPostBeans(),
//                new Predicate() {
//                    public boolean evaluate(Object object) {
//                        PostBean post = (PostBean) object;
//                        return user.getProgram().equals(post.getPost().getPostAuthor().getProgram());
////                        return true;
//                    }
//                },
//                feed);
//        return feed;
        final Post[] feedPosts = PostDbManager.getSharedInstance().getUserFeedPosts(user);

        return new SortedArrayList<PostBean>() {{
            for (Post post : feedPosts) {
                PostBean bean = new PostBean();
                bean.setPost(post);
                insertSorted(bean);
            }
        }};
    }
}
