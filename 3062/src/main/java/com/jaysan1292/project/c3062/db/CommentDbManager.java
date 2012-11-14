package com.jaysan1292.project.c3062.db;

import com.jaysan1292.project.common.data.Comment;
import com.jaysan1292.project.common.data.Post;
import org.apache.commons.dbutils.DbUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * Date: 13/11/12
 * Time: 12:59 AM
 *
 * @author Jason Recillo
 */
public class CommentDbManager extends BaseDbManager<Comment> {
    private static CommentDbManager sharedInstance;
    private static final String TABLE_NAME = "comment_t";
    private static final String ID_COLUMN = "comment_id";
    private static final String AUTHOR_ID_COLUMN = "comment_author_id";
    private static final String CONTENT_COLUMN = "comment_body";
    private static final String DATE_COLUMN = "comment_date";
    private static final String PARENT_POST_ID_COLUMN = "post_id";
    private static final Comment[] EMPTY_COMMENTS = new Comment[0];

    public static CommentDbManager getSharedInstance() {
        if (sharedInstance == null) sharedInstance = new CommentDbManager();
        return sharedInstance;
    }

    private CommentDbManager() {
        super(Comment.class);
    }

    protected String tableName() {
        return TABLE_NAME;
    }

    protected String idColumnName() {
        return ID_COLUMN;
    }

    protected Comment buildObject(ResultSet rs) throws SQLException {
        Comment comment = new Comment();
        comment.setId(rs.getLong(ID_COLUMN));
        comment.setCommentAuthor(UserDbManager.getSharedInstance().get(rs.getLong(AUTHOR_ID_COLUMN)));
        comment.setCommentBody(rs.getString(CONTENT_COLUMN));
        comment.setCommentDate(new Date(rs.getLong(DATE_COLUMN)));
        comment.setParentPostId(rs.getLong(PARENT_POST_ID_COLUMN));
        return comment;
    }

    protected int doUpdate(Connection conn, Comment item) throws SQLException {
        String query = "UPDATE " + TABLE_NAME + " SET " +
                       AUTHOR_ID_COLUMN + "=?, " +
                       CONTENT_COLUMN + "=?, " +
                       DATE_COLUMN + "=?, " +
                       PARENT_POST_ID_COLUMN + "=? " +
                       "WHERE " + ID_COLUMN + "=?";

        return RUN.update(conn, query,
                          item.getCommentAuthor().getId(),
                          item.getCommentBody(),
                          item.getCommentDate().getTime(),
                          item.getParentPostId(),
                          item.getId());
    }

    protected void doInsert(Connection conn, Comment item) throws SQLException {
        String query = "INSERT INTO " + TABLE_NAME + " (" +
                       AUTHOR_ID_COLUMN + ", " +
                       CONTENT_COLUMN + ", " +
                       DATE_COLUMN + ", " +
                       PARENT_POST_ID_COLUMN + ") VALUES (?, ?, ?, ?)";
        RUN.update(conn, query,
                   item.getCommentAuthor().getId(),
                   item.getCommentBody(),
                   item.getCommentDate().getTime(),
                   item.getParentPostId());
    }

    ///////////////////////////////////////////////////////////////////////////

    public Comment[] getComments(Post post) {
        Connection conn = null;
        try {
            conn = openDatabaseConnection();
            String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + PARENT_POST_ID_COLUMN + "=?";
            Comment[] comments = RUN.query(conn, query, getArrayResultSetHandler(), post.getId());

            if (comments == null) {
                return EMPTY_COMMENTS;
            }

            return comments;
        } catch (SQLException e) {
            return EMPTY_COMMENTS;
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }
}
