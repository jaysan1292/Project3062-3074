package com.jaysan1292.project.c3062.db;

import com.jaysan1292.project.c3062.WebAppCommon;
import com.jaysan1292.project.common.data.BaseEntity;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.lang3.time.StopWatch;
import org.intellij.lang.annotations.Language;

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
        Connection conn = null;
        try {
            try {
                Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            } catch (ClassNotFoundException ignored) {}

            //TODO: Foreign key constraints
            StopWatch watch = new StopWatch(); watch.start();
            WebAppCommon.log.info("Initializing database...");
            WebAppCommon.log.info("Creating tables");
            conn = openDatabaseConnection();

            //region Table SQL
            @Language("Derby")
            String program =
                    "CREATE TABLE program_t(\n" +
                    "  program_id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\n" +
                    "  program_code CHAR(4) NOT NULL,\n" +
                    "  program_name VARCHAR(96) NOT NULL,\n" +
                    "  CONSTRAINT program_pk PRIMARY KEY (program_id)\n" +
                    ')';
            @Language("Derby")
            String user =
                    "CREATE TABLE user_t(\n" +
                    "  user_id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\n" +
                    "  first_name VARCHAR(32) NOT NULL,\n" +
                    "  last_name VARCHAR(32) NOT NULL,\n" +
                    "  email VARCHAR(24) NOT NULL,\n" +
                    "  student_number CHAR(9) NOT NULL, -- This is the \"username\" for logging in\n" +
                    "  program_id BIGINT NOT NULL, -- Foreign key to program table\n" +
                    "  password CHAR(64) NOT NULL, -- Stored as a 64-character encrypted hash\n" +
                    "  CONSTRAINT user_pk PRIMARY KEY (user_id),  CONSTRAINT user_fk FOREIGN KEY (program_id)\n" +
                    "  REFERENCES program_t (program_id)\n" +
                    ')';
            @Language("Derby")
            String post =
                    "CREATE TABLE post_t(\n" +
                    "  post_id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\n" +
                    "  post_date DATE NOT NULL,\n" +
                    "  post_author_id BIGINT NOT NULL, -- Foreign key to user table\n" +
                    "  post_content LONG VARCHAR NOT NULL,\n" +
                    "  CONSTRAINT post_pl PRIMARY KEY (post_id),\n" +
                    "  CONSTRAINT post_fk FOREIGN KEY (post_author_id)\n" +
                    "  REFERENCES user_t (user_id)\n" +
                    ')';
            @Language("Derby")
            String comment =
                    "CREATE TABLE comment_t(\n" +
                    "  comment_id BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\n" +
                    "  comment_author_id BIGINT NOT NULL, -- Foreign key to user table\n" +
                    "  comment_body LONG VARCHAR NOT NULL,\n" +
                    "  comment_date DATE NOT NULL,\n" +
                    "  post_id BIGINT NOT NULL, -- Foreign key to post table\n" +
                    "  CONSTRAINT comment_pk PRIMARY KEY (comment_id),\n" +
                    "  CONSTRAINT comment_fk FOREIGN KEY (post_id)\n" +
                    "  REFERENCES post_t (post_id)\n" +
                    ')';
            //endregion

            //region Create tables
            WebAppCommon.log.trace("Creating program table.");
            try {
                RUN.update(conn, program);
            } catch (SQLException e) {
                if (DerbyHelper.tableAlreadyExists(e)) {
                    WebAppCommon.log.trace("Program table already exists! Moving on then.");
                } else {
                    throw e;
                }
            }

            WebAppCommon.log.trace("Creating user table.");
            try {
                RUN.update(conn, user);
            } catch (SQLException e) {
                if (DerbyHelper.tableAlreadyExists(e)) {
                    WebAppCommon.log.trace("User table already exists! Moving on then.");
                } else {
                    throw e;
                }
            }

            WebAppCommon.log.trace("Creating post table.");
            try {
                RUN.update(conn, post);
            } catch (SQLException e) {
                if (DerbyHelper.tableAlreadyExists(e)) {
                    WebAppCommon.log.trace("Post table already exists! Moving on then.");
                } else {
                    throw e;
                }
            }

            WebAppCommon.log.trace("Creating comment table.");
            try {
                RUN.update(conn, comment);
            } catch (SQLException e) {
                if (DerbyHelper.tableAlreadyExists(e)) {
                    WebAppCommon.log.trace("Comment table already exists! Moving on then.");
                } else {
                    throw e;
                }
            }
            //endregion

            watch.stop();
            WebAppCommon.log.info(String.format("Initializing database complete! Finished in %s.", watch.toString()));
        } catch (SQLException e) {
            WebAppCommon.log.error("There was an error creating and populating database tables. ABORT ABORT", e);
            System.exit(0);
        } finally {
            DbUtils.closeQuietly(conn);
        }

        //region Shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread() {
            //Register shutdown hook to clear the tables and shut down Derby
            public void run() {
                WebAppCommon.log.info("Shutting down database.");
            }
        });
        //endregion
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
