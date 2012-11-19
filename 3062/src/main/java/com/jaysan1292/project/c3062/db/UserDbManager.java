package com.jaysan1292.project.c3062.db;

import com.jaysan1292.project.c3062.WebAppCommon;
import com.jaysan1292.project.common.data.Program;
import com.jaysan1292.project.common.data.User;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * Date: 12/11/12
 * Time: 9:27 PM
 *
 * @author Jason Recillo
 */
public class UserDbManager extends BaseDbManager<User> {
    private static UserDbManager sharedInstance;
    public static final String TABLE_NAME = "user_t";
    public static final String ID_COLUMN = "user_id";
    public static final String FIRST_NAME_COLUMN = "first_name";
    public static final String LAST_NAME_COLUMN = "last_name";
    public static final String EMAIL_COLUMN = "email";
    public static final String STUDENT_NUMBER_COLUMN = "student_number";
    public static final String PROGRAM_ID_COLUMN = "program_id";
    public static final String PASSWORD_COLUMN = "password";
    private static final User[] EMPTY_USERS = new User[0];

    public static UserDbManager getSharedInstance() {
        if (sharedInstance == null) sharedInstance = new UserDbManager();
        return sharedInstance;
    }

    private UserDbManager() {
        super(User.class);
    }

    protected String tableName() {
        return TABLE_NAME;
    }

    protected String idColumnName() {
        return ID_COLUMN;
    }

    protected User buildObject(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong(ID_COLUMN));
        user.setFirstName(rs.getString(FIRST_NAME_COLUMN));
        user.setLastName(rs.getString(LAST_NAME_COLUMN));
        user.setEmail(rs.getString(EMAIL_COLUMN));
        user.setStudentNumber(rs.getString(STUDENT_NUMBER_COLUMN));
        user.setProgram(ProgramDbManager.getSharedInstance().get(rs.getLong(PROGRAM_ID_COLUMN)));
        user.setPasswordDirect(rs.getString(PASSWORD_COLUMN));
        return user;
    }

    protected int doUpdate(Connection conn, User item) throws SQLException {
        String query = "UPDATE " + TABLE_NAME + " SET " +
                       FIRST_NAME_COLUMN + "=?, " +
                       LAST_NAME_COLUMN + "=?, " +
                       EMAIL_COLUMN + "=?, " +
                       STUDENT_NUMBER_COLUMN + "=?, " +
                       PROGRAM_ID_COLUMN + "=?, " +
                       PASSWORD_COLUMN + "=? " +
                       "WHERE " + ID_COLUMN + "=?";

        return RUN.update(conn, query,
                          item.getFirstName(),
                          item.getLastName(),
                          item.getEmail(),
                          item.getStudentNumber(),
                          item.getProgram().getId(),
                          item.getPassword(),
                          item.getId());
    }

    protected void doInsert(Connection conn, User user) throws SQLException {
        String query = "INSERT INTO " + TABLE_NAME + " (" +
                       FIRST_NAME_COLUMN + ", " +
                       LAST_NAME_COLUMN + ", " +
                       EMAIL_COLUMN + ", " +
                       STUDENT_NUMBER_COLUMN + ", " +
                       PROGRAM_ID_COLUMN + ", " +
                       PASSWORD_COLUMN + ") VALUES (?, ?, ?, ?, ?, ?)";
        RUN.update(conn, query,
                   user.getFirstName(),
                   user.getLastName(),
                   user.getEmail(),
                   user.getStudentNumber(),
                   user.getProgram().getId(),
                   user.getPassword());
    }

    ///////////////////////////////////////////////////////////////////////////

    public User getUser(String studentId) throws SQLException {
        Connection conn = null;
        try {
            conn = openDatabaseConnection();
            String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + STUDENT_NUMBER_COLUMN + "=?";
            User user = RUN.query(conn, query, getResultSetHandler(), studentId);

            if (user == null) {
                throw new SQLException(String.format("The requested item was not found in the database. Query: [%s] Parameters: %s", query, user));
            }

            return user;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage(), e);
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    public User[] getUsers(Program program) throws SQLException {
        Connection conn = null;
        try {
            conn = openDatabaseConnection();
            String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + PROGRAM_ID_COLUMN + "=?";
            User[] users = RUN.query(conn, query, getArrayResultSetHandler(), program.getId());

            if (users == null) {
                throw new SQLException(String.format("The requested item was not found in the database. Query: [%s] Parameters: %s", query, program.getId()));
            }

            return users;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage(), e);
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    public User[] getClassmates(User user) {
        Program program = user.getProgram();
        Connection conn = null;
        try {
            conn = openDatabaseConnection();
            String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + PROGRAM_ID_COLUMN + "=? " +
                           "ORDER BY RANDOM() FETCH FIRST 16 ROWS ONLY";
            User[] users = RUN.query(conn, query, getArrayResultSetHandler(), program.getId());

            if (users == null) {
                throw new SQLException(String.format("The requested item was not found in the database. Query: [%s] Parameters: %s", query, program.getId()));
            }

            return users;
        } catch (SQLException e) {
            WebAppCommon.log.error(e.getMessage(), e);
            return EMPTY_USERS;
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    public boolean isStudentNumberUnique(String studentNumber) {
        Connection conn = null;
        try {
            conn = openDatabaseConnection();
            String query = "SELECT " + ID_COLUMN + " FROM " + TABLE_NAME + " WHERE " + STUDENT_NUMBER_COLUMN + "=?";

            Long id = RUN.query(conn, query, new ScalarHandler<Long>(ID_COLUMN), studentNumber);

            // if id is null, the student number was not found, which means that
            // the given student number does not yet exist in the database.
            return id == null;
        } catch (SQLException e) {
            WebAppCommon.log.error(e.getMessage(), e);
            return false;
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    public boolean isEmailUnique(String email) {
        Connection conn = null;
        try {
            conn = openDatabaseConnection();
            String query = "SELECT " + ID_COLUMN + " FROM " + TABLE_NAME + " WHERE " + EMAIL_COLUMN + "=?";

            Long id = RUN.query(conn, query, new ScalarHandler<Long>(ID_COLUMN), email);

            return id == null;
        } catch (SQLException e) {
            WebAppCommon.log.error(e.getMessage(), e);
            return false;
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }
}
