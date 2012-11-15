package com.jaysan1292.project.c3062.db;

import com.jaysan1292.project.c3062.WebAppCommon;
import com.jaysan1292.project.common.data.Post;
import com.jaysan1292.project.common.data.User;
import com.jaysan1292.project.common.data.beans.PostBean;
import com.jaysan1292.project.common.util.SortedArrayList;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;

/** @author Jason Recillo */
public class PostDbManager extends BaseDbManager<Post> {
    private static PostDbManager sharedInstance;
    public static final String TABLE_NAME = "post_t";
    public static final String ID_COLUMN = "post_id";
    public static final String DATE_COLUMN = "post_date";
    public static final String AUTHOR_ID_COLUMN = "post_author_id";
    public static final String CONTENT_COLUMN = "post_content";
    private static final Post[] EMPTY_POSTS = new Post[0];
    private static final PostBean[] EMPTY_POST_BEANS = new PostBean[0];

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
                          item.getPostContent(),
                          item.getId());
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

    public synchronized Post[] getPosts(User user) throws SQLException {
        Connection conn = null;
        try {
            conn = openDatabaseConnection();
            String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + AUTHOR_ID_COLUMN + "=?";
            Post[] posts = RUN.query(conn, query, getArrayResultSetHandler(), user.getId());

            if ((posts == null)) {
                return EMPTY_POSTS;
            }

            return posts;
        } catch (SQLException e) {
            WebAppCommon.log.error(e.getMessage(), e);
            return EMPTY_POSTS;
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    public synchronized PostBean[] getPostBeans(User user) {
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

            return userPosts.toArray(new PostBean[userPosts.size()]);
        } catch (SQLException e) {
            WebAppCommon.log.error(e.getMessage(), e);
            return EMPTY_POST_BEANS;
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    public synchronized PostBean[] getAllPostBeans() {
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

            return allPosts.toArray(new PostBean[allPosts.size()]);
        } catch (Exception e) {
            WebAppCommon.log.error(e.getMessage(), e);
            return EMPTY_POST_BEANS;
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    public synchronized Post[] getUserFeedPosts(User user) {
        Connection conn = null;
        try {
            conn = openDatabaseConnection();
            // Select all posts, where the post author's program
            // is the same as the given user's program
            String query = "SELECT * FROM " + PostDbManager.TABLE_NAME + ' ' +
                           "WHERE " + PostDbManager.AUTHOR_ID_COLUMN + " IN " +
                           "(SELECT " + UserDbManager.ID_COLUMN + ' ' +
                           "FROM " + UserDbManager.TABLE_NAME + " WHERE " +
                           UserDbManager.PROGRAM_ID_COLUMN + "=?) " +
                           "ORDER BY " + PostDbManager.DATE_COLUMN + " DESC";
            Post[] feedPosts = RUN.query(conn, query, getArrayResultSetHandler(), user.getProgram().getId());

            if (feedPosts == null) return EMPTY_POSTS;

            return feedPosts;
        } catch (SQLException e) {
            WebAppCommon.log.error(e.getMessage(), e);
            return EMPTY_POSTS;
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    public synchronized int getCommentCount(Post post) {
        Connection conn = null;
        try {
            conn = openDatabaseConnection();
            // Get the number of comments for the given post
            String query = "SELECT COUNT(*) FROM " + CommentDbManager.TABLE_NAME + ' ' +
                           "WHERE " + CommentDbManager.PARENT_POST_ID_COLUMN + " IN " +
                           "(SELECT " + PostDbManager.ID_COLUMN + " FROM " + PostDbManager.TABLE_NAME + ' ' +
                           "WHERE " + PostDbManager.ID_COLUMN + "=?)";
            Integer count = RUN.query(conn, query, new ScalarHandler<Integer>(1), post.getId());

            if (count == null) {
                count = 0;
            }

            return count;
        } catch (SQLException e) {
            WebAppCommon.log.error(e.getMessage(), e);
            return 0;
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }
}
