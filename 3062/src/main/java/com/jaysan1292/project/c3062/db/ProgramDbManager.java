package com.jaysan1292.project.c3062.db;

import com.jaysan1292.project.common.data.Program;
import org.apache.commons.dbutils.DbUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * Date: 31/10/12
 * Time: 11:24 PM
 *
 * @author Jason Recillo
 */
public class ProgramDbManager extends BaseDbManager<Program> {
    private static ProgramDbManager sharedInstance;
    public static final String TABLE_NAME = "program_t";
    public static final String ID_COLUMN = "program_id";
    public static final String CODE_COLUMN = "program_code";
    public static final String NAME_COLUMN = "program_name";

    public static synchronized ProgramDbManager getSharedInstance() {
        if (sharedInstance == null) sharedInstance = new ProgramDbManager();
        return sharedInstance;
    }

    private ProgramDbManager() {
        super(Program.class);
    }

    protected String tableName() {
        return TABLE_NAME;
    }

    protected String idColumnName() {
        return ID_COLUMN;
    }

    protected Program buildObject(ResultSet rs) throws SQLException {
        Program program = new Program();
        program.setId(rs.getLong(ID_COLUMN));
        program.setProgramCode(rs.getString(CODE_COLUMN));
        program.setProgramName(rs.getString(NAME_COLUMN));
        return program;
    }

    protected int doUpdate(Connection conn, Program item) throws SQLException {
        throw new UnsupportedOperationException();
    }

    protected void doInsert(Connection conn, Program item) throws SQLException {
        String query = "INSERT INTO " + TABLE_NAME + " (" + CODE_COLUMN + ", " + NAME_COLUMN + ") VALUES (?, ?)";
        RUN.update(conn, query, item.getProgramCode(), item.getProgramName());
    }

    protected void doDelete(Connection conn, Program item) throws SQLException {
        throw new UnsupportedOperationException();
    }

    ///////////////////////////////////////////////////////////////////////////

    public Program getProgram(String programCode) throws SQLException {
        Connection conn = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?", tableName(), CODE_COLUMN);
        try {
            conn = openDatabaseConnection();
            Program program = RUN.query(conn, query, getResultSetHandler(), programCode);

            if (program == null) {
                throw new SQLException(String.format("The requested item was not found in the database. Query: [%s] Parameters: %s", query, program));
            }

            return program;
        } catch (SQLException e) {
            throw new SQLException(e.getMessage(), e);
        } finally {
            DbUtils.closeQuietly(conn);
        }
    }
}
