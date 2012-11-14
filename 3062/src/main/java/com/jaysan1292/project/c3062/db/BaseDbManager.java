package com.jaysan1292.project.c3062.db;

import com.jaysan1292.project.c3062.WebAppCommon;
import com.jaysan1292.project.common.data.BaseEntity;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//TODO: Remove sharedInstance from all subclasses, and just make each method synchronized

/**
 * Created with IntelliJ IDEA.
 * Date: 31/10/12
 * Time: 8:07 PM
 *
 * @author Jason Recillo
 */
public abstract class BaseDbManager<T extends BaseEntity> {
    public static final String DB_NAME = "gbc_community";
    static final QueryRunner RUN = new QueryRunner();
    private Class<T> cls;

    public BaseDbManager(Class<T> cls) {
        this.cls = cls;
    }

    protected ResultSetHandler<T> getResultSetHandler() {
        return new ResultSetHandler<T>() {
            public T handle(ResultSet rs) throws SQLException {
                if (!rs.next()) return null;

                return buildObject(rs);
            }
        };
    }

    protected ResultSetHandler<T[]> getArrayResultSetHandler() {
        return new ResultSetHandler<T[]>() {
            @SuppressWarnings("unchecked")
            public T[] handle(ResultSet rs) throws SQLException {
                if (!rs.next()) return null;

                ArrayList<T> items = new ArrayList<T>();
                do {
                    items.add(buildObject(rs));
                } while (rs.next());

                return items.toArray((T[]) Array.newInstance(cls, items.size()));
            }
        };
    }

    protected abstract String tableName();

    protected abstract String idColumnName();

    protected abstract T buildObject(ResultSet rs) throws SQLException;

    public T get(long id) throws SQLException {
        Connection conn = null;
        String query = "SELECT * FROM " + tableName() + " WHERE " + idColumnName() + "=?";
        try {
            conn = openDatabaseConnection();
            T item = RUN.query(conn, query, getResultSetHandler(), id);

            if (item == null) {
                throw new SQLException(String.format("The requested item was not found in the database. Query: [%s] Parameters: %s", query, id));
            }

            return item;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage(), e);
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    @SuppressWarnings("unchecked")
    public T[] getAll() {
        Connection conn = null;
        try {
            conn = openDatabaseConnection();
            String query = "SELECT * FROM " + tableName();

            T[] items = RUN.query(conn, query, getArrayResultSetHandler());

            if (items == null) throw new SQLException();

            return items;
        } catch (SQLException e) {
            WebAppCommon.log.error(e.getMessage(), e);
            return (T[]) new ArrayList<T>().toArray();
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    public void update(T item) throws SQLException {
        Connection conn = null;
        try {
            conn = openDatabaseConnection();
            doUpdate(conn, item);
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    public void insert(T item) throws SQLException {
        Connection conn = null;
        try {
            conn = openDatabaseConnection();
            doInsert(conn, item);
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    public int getCount() {
        Connection conn = null;
        try {
            conn = openDatabaseConnection();
            return RUN.query(conn, "SELECT COUNT(*) FROM " + tableName(), new ScalarHandler<Integer>());
        } catch (SQLException e) {
            return 0;
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    protected abstract int doUpdate(Connection conn, T item) throws SQLException;

    protected abstract void doInsert(Connection conn, T item) throws SQLException;

    protected static Connection openDatabaseConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:derby:" + DB_NAME);
    }
}
