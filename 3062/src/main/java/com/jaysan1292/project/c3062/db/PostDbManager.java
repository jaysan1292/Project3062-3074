package com.jaysan1292.project.c3062.db;

import com.jaysan1292.project.c3062.WebAppCommon;
import com.jaysan1292.project.common.data.Post;
import com.jaysan1292.project.common.data.User;
import com.jaysan1292.project.common.data.beans.PostBean;
import com.jaysan1292.project.common.util.SortedArrayList;
import org.apache.commons.dbutils.DbUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;

/** @author Jason Recillo */
public class PostDbManager extends BaseDbManager<Post> {
    private static PostDbManager sharedInstance;
    private static final String TABLE_NAME = "post_t";
    private static final String ID_COLUMN = "post_id";
    private static final String DATE_COLUMN = "post_date";
    private static final String AUTHOR_ID_COLUMN = "post_author_id";
    private static final String CONTENT_COLUMN = "post_content";

    public static synchronized PostDbManager getSharedInstance() {
        if (sharedInstance == null) sharedInstance = new PostDbManager();
        return sharedInstance;
    }

    private PostDbManager() {
        super(Post.class);
    }

    protected String tableName() {
        return TABLE_NAME;
    }

    protected String idColumnName() {
        return ID_COLUMN;
    }

    protected Post buildObject(ResultSet rs) throws SQLException {
        Post post = new Post();
        post.setId(rs.getLong(ID_COLUMN));
        post.setPostDate(new Date(rs.getLong(DATE_COLUMN)));
        post.setPostAuthor(UserDbManager.getSharedInstance().get(rs.getLong(AUTHOR_ID_COLUMN)));
        post.setPostContent(rs.getString(CONTENT_COLUMN));
        return post;
    }

    protected int doUpdate(Connection conn, Post item) throws SQLException {
        String query = "UPDATE " + TABLE_NAME + " SET " +
                       DATE_COLUMN + "=?, " +
                       AUTHOR_ID_COLUMN + "=?, " +
                       CONTENT_COLUMN + "=? " +
                       "WHERE " + ID_COLUMN + "=?";

        return RUN.update(conn, query,
                          item.getPostDate().getTime(),
                          item.getPostAuthor().getId(),
                          item.getPostContent());
    }

    protected void doInsert(Connection conn, Post item) throws SQLException {
        String query = "INSERT INTO " + TABLE_NAME + " (" +
                       DATE_COLUMN + ", " +
                       AUTHOR_ID_COLUMN + ", " +
                       CONTENT_COLUMN + ") VALUES (?, ?, ?)";
        RUN.update(conn, query,
                   item.getPostDate().getTime(),
                   item.getPostAuthor().getId(),
                   item.getPostContent());
    }

    ///////////////////////////////////////////////////////////////////////////

    public synchronized SortedArrayList<Post> getPosts(User user) throws SQLException {
        Connection conn = null;
        try {
            conn = openDatabaseConnection();
            String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + AUTHOR_ID_COLUMN + "=?";
            Post[] posts = RUN.query(conn, query, getArrayResultSetHandler(), user.getId());

            if ((posts == null)) {
                return new SortedArrayList<Post>();
            }

            return new SortedArrayList<Post>(posts);
        } catch (SQLException e) {
            WebAppCommon.log.error(e.getMessage(), e);
            return new SortedArrayList<Post>();
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    public synchronized SortedArrayList<PostBean> getPostBeans(User user) {
        Connection conn = null;
        try {
            conn = openDatabaseConnection();
            String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + AUTHOR_ID_COLUMN + "=?";
            Post[] posts = RUN.query(conn, query, getArrayResultSetHandler(), user.getId());

            SortedArrayList<PostBean> userPosts = new SortedArrayList<PostBean>();
            for (Post post : posts) {
                PostBean bean = new PostBean();
                bean.setPost(post);
                bean.setComments(Arrays.asList(CommentDbManager.getSharedInstance().getComments(post)));
                userPosts.insertSorted(bean);
            }

            return userPosts;
        } catch (SQLException e) {
            WebAppCommon.log.error(e.getMessage(), e);
            return new SortedArrayList<PostBean>();
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    public synchronized SortedArrayList<PostBean> getAllPostBeans() {
        Connection conn = null;
        try {
            Post[] posts = getAll();
            SortedArrayList<PostBean> allPosts = new SortedArrayList<PostBean>();
            for (Post post : posts) {
                PostBean bean = new PostBean();
                bean.setPost(post);
                bean.setComments(Arrays.asList(CommentDbManager.getSharedInstance().getComments(post)));
                allPosts.insertSorted(bean);
            }

            return allPosts;
        } catch (Exception e) {
            WebAppCommon.log.error(e.getMessage(), e);
            return new SortedArrayList<PostBean>();
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }
}
