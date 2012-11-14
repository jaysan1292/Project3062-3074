package com.jaysan1292.project.c3062.db;

import com.jaysan1292.project.common.data.Program;
import com.jaysan1292.project.common.data.User;
import com.jaysan1292.project.common.data.beans.UserBean;
import com.jaysan1292.project.common.security.UserPasswordPair;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.ResultSetHandler;

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
    private static final String TABLE_NAME = "user_t";
    private static final String ID_COLUMN = "user_id";
    private static final String FIRST_NAME_COLUMN = "first_name";
    private static final String LAST_NAME_COLUMN = "last_name";
    private static final String EMAIL_COLUMN = "email";
    private static final String STUDENT_NUMBER_COLUMN = "student_number";
    private static final String PROGRAM_ID_COLUMN = "program_id";
    private static final String PASSWORD_COLUMN = "password";

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
        return user;
    }

    protected int doUpdate(Connection conn, User item) throws SQLException {
        String query = "UPDATE " + TABLE_NAME + " SET " +
                       FIRST_NAME_COLUMN + "=?, " +
                       LAST_NAME_COLUMN + "=?, " +
                       EMAIL_COLUMN + "=?, " +
                       STUDENT_NUMBER_COLUMN + "=?, " +
                       PROGRAM_ID_COLUMN + "=?" +
                       "WHERE " + ID_COLUMN + "=?";

        return RUN.update(conn, query,
                          item.getFirstName(),
                          item.getLastName(),
                          item.getEmail(),
                          item.getStudentNumber(),
                          item.getProgram().getId(),
                          item.getId());
    }

    public void insert(UserBean item) throws SQLException {
        Connection conn = null;
        try {
            conn = openDatabaseConnection();
            doInsert(conn, item);
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    protected void doInsert(Connection conn, User item) throws SQLException {
        doInsert(conn, new UserBean(item));
    }

    protected void doInsert(Connection conn, UserBean user) throws SQLException {
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
                   user.getPasswordString());
    }

    ///////////////////////////////////////////////////////////////////////////

    public User getUser(String studentId) throws SQLException {
        Connection c = openDatabaseConnection();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + STUDENT_NUMBER_COLUMN + "=?";
        try {
            User user = RUN.query(c, query, getResultSetHandler(), studentId);

            if (user == null) {
                throw new SQLException(String.format("The requested item was not found in the database. Query: [%s] Parameters: %s", query, user));
            }

            return user;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage(), e);
        } finally {
            DbUtils.closeQuietly(c);
        }
    }

    public User[] getUsers(Program program) throws SQLException {
        Connection c = openDatabaseConnection();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + PROGRAM_ID_COLUMN + "=?";
        try {
            User[] users = RUN.query(c, query, getArrayResultSetHandler(), program.getId());

            if (users == null) {
                throw new SQLException(String.format("The requested item was not found in the database. Query: [%s] Parameters: %s", query, program.getId()));
            }

            return users;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage(), e);
        } finally {
            DbUtils.closeQuietly(c);
        }
    }

    public UserBean getUserBean(long id) throws SQLException {
        Connection conn = null;
        try {
            conn = openDatabaseConnection();
            String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + ID_COLUMN + "=?";
            UserBean user = RUN.query(conn, query, new ResultSetHandler<UserBean>() {
                public UserBean handle(ResultSet rs) throws SQLException {
                    if (!rs.next()) return null;

                    UserBean u = new UserBean();
                    u.setUser(buildObject(rs));
                    u.getPassword().setPasswordDirect(rs.getString(PASSWORD_COLUMN));

                    return u;
                }
            }, id);

            if (user == null) {
                throw new SQLException(String.format("The requested item was not found in the database. Query: [%s] Parameters: %s", query, id));
            }

            return user;
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }

    public UserPasswordPair getPassword(User user) throws SQLException {
        return getUserBean(user.getId()).getPassword();
    }
}
