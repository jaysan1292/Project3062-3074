package com.jaysan1292.project.c3062.db;

import com.jaysan1292.project.common.data.BaseEntity;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;

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

    private String tableName;

    static {
        DatabaseInitializer.initialize();
    }

    BaseDbManager(String tableName) {
        this.tableName = tableName;
    }

    public abstract String getIdColumnName();

    protected abstract ResultSetHandler<T> getResultSetHandler();

    protected abstract ResultSetHandler<T[]> getArrayResultSetHandler();

    //region Single items

    public int put(T value) throws SQLException {
        Connection connection = openDatabaseConnection();
        try {
            return doPut(connection, value);
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    public T get(long id) throws SQLException {
        Connection connection = openDatabaseConnection();

        T item = null;
        try {
            String query = doGet(connection, id);
            item = RUN.query(connection, query, getResultSetHandler(), id);

            if (item == null) {
                String errorMessage = "The requested item was not found in the database. Query: %s; Parameters: %s";
                throw new SQLException(String.format(errorMessage, query, id));
            }
        } finally {
            DbUtils.closeQuietly(connection);
        }

        return item;
    }

    public int update(T newValue) throws SQLException {
        Connection connection = openDatabaseConnection();
        try {
            return doUpdate(connection, newValue);
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    public int delete(long id) throws SQLException {
        Connection connection = openDatabaseConnection();
        try {
            return doDelete(connection, id);
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    //endregion

    public int putBatch(Collection<T> values) throws SQLException {
        Connection connection = openDatabaseConnection();
        try {
            int count = 0;
            for (T value : values) {
                count += doPut(connection, value);
            }
            return count;
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    public Collection<T> getBatch(String[] columns, Object... values) throws SQLException {
//        Connection connection = openDatabaseConnection();
//        try {
//
//        } finally {
//            DbUtils.closeQuietly(connection);
//        }
        throw new UnsupportedOperationException();
    }

    public int updateBatch(Collection<T> newValues) throws SQLException {
        Connection connection = openDatabaseConnection();
        try {
            int count = 0;
            for (T newValue : newValues) {
                count += doUpdate(connection, newValue);
            }
            return count;
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    public int deleteBatch(Collection<T> valuesToDelete) throws SQLException {
        Connection connection = openDatabaseConnection();
        try {
            int count = 0;
            for (T value : valuesToDelete) {
                count += doDelete(connection, value.getId());
            }
            return count;
        } finally {
            DbUtils.closeQuietly(connection);
        }
    }

    protected abstract String doGet(Connection connection, long id) throws SQLException;

    protected abstract T[] doGet(Connection connection, String[] columns, Object... params) throws SQLException;

    protected abstract int doPut(Connection connection, T value) throws SQLException;

    protected abstract int doUpdate(Connection connection, T newValue) throws SQLException;

    protected abstract int doDelete(Connection connection, long id) throws SQLException;

    protected abstract void verifyColumns(String[] columns, Object... params) throws SQLException;

    public static Connection openDatabaseConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:derby:" + DB_NAME + ";create=true");
    }
}
