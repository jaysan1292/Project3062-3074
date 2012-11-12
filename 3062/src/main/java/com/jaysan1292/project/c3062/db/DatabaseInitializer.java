package com.jaysan1292.project.c3062.db;

import com.jaysan1292.project.c3062.WebAppCommon;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.intellij.lang.annotations.Language;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * Date: 12/11/12
 * Time: 5:11 PM
 *
 * @author Jason Recillo
 */
class DatabaseInitializer {
    private static boolean _initialized;

    private DatabaseInitializer() {}

    public static void initialize() {
        if (_initialized) {
            throw new IllegalStateException("Database is already initialized!");
        }

        Connection conn = null;
        try {
            try {
                Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            } catch (ClassNotFoundException ignored) {}

            //TODO: Foreign key constraints
            StopWatch watch = new StopWatch(); watch.start();
            WebAppCommon.log.info("Initializing database...");
            WebAppCommon.log.info("Creating tables");
            conn = BaseDbManager.openDatabaseConnection();

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
                BaseDbManager.RUN.update(conn, program);
            } catch (SQLException e) {
                if (DerbyHelper.tableAlreadyExists(e)) {
                    WebAppCommon.log.trace("Program table already exists! Moving on then.");
                } else {
                    throw e;
                }
            }

            WebAppCommon.log.trace("Creating user table.");
            try {
                BaseDbManager.RUN.update(conn, user);
            } catch (SQLException e) {
                if (DerbyHelper.tableAlreadyExists(e)) {
                    WebAppCommon.log.trace("User table already exists! Moving on then.");
                } else {
                    throw e;
                }
            }

            WebAppCommon.log.trace("Creating post table.");
            try {
                BaseDbManager.RUN.update(conn, post);
            } catch (SQLException e) {
                if (DerbyHelper.tableAlreadyExists(e)) {
                    WebAppCommon.log.trace("Post table already exists! Moving on then.");
                } else {
                    throw e;
                }
            }

            WebAppCommon.log.trace("Creating comment table.");
            try {
                BaseDbManager.RUN.update(conn, comment);
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
            System.exit(1);
        } finally {
            DbUtils.closeQuietly(conn);
        }
        _initialized = true;
    }
}
