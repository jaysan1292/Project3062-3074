package com.jaysan1292.project.c3062.db;

import com.jaysan1292.project.common.data.Comment;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * Date: 04/11/12
 * Time: 8:07 PM
 *
 * @author Jason Recillo
 */
public class CommentDbManager extends BaseDbManager<Comment> {
    CommentDbManager() {
        super("comment");
    }

    public String getIdColumnName() {
        return "comment_id";
    }

    protected ResultSetHandler<Comment> getResultSetHandler() {
        return null;  //TODO: Auto-generated method stub
    }

    protected ResultSetHandler<Comment[]> getArrayResultSetHandler() {
        return null;  //TODO: Auto-generated method stub
    }

    protected String doGet(Connection connection, long id) throws SQLException {
        return null;  //TODO: Auto-generated method stub
    }

    protected Comment[] doGet(Connection connection, String[] columns, Object... params) throws SQLException {
        return new Comment[0];  //TODO: Auto-generated method stub
    }

    protected int doPut(Connection connection, Comment value) throws SQLException {
        return 0;  //TODO: Auto-generated method stub
    }

    protected int doUpdate(Connection connection, Comment newValue) throws SQLException {
        return 0;  //TODO: Auto-generated method stub
    }

    protected int doDelete(Connection connection, long id) throws SQLException {
        return 0;  //TODO: Auto-generated method stub
    }

    protected void verifyColumns(String[] columns, Object... params) throws SQLException {
        //TODO: Auto-generated method stub
    }
}
