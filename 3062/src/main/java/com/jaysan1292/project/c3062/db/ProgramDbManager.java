package com.jaysan1292.project.c3062.db;

import com.jaysan1292.project.common.data.Program;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * Date: 31/10/12
 * Time: 11:24 PM
 *
 * @author Jason Recillo
 */
public class ProgramDbManager extends BaseDbManager<Program> {
    public static final String TABLE_NAME = "program_t";
    public static final String ID_COLUMN = "program_id";
    public static final String CODE_COLUMN = "program_code";
    public static final String NAME_COLUMN = "program_name";

    public ProgramDbManager() {
        super(TABLE_NAME);
    }

    public String getIdColumnName() {
        return ID_COLUMN;
    }

    public ResultSetHandler<Program> getResultSetHandler() {
        return new ResultSetHandler<Program>() {
            public Program handle(ResultSet rs) throws SQLException {
                if (!rs.next()) return null;

                Program program = new Program();
                program.setId(rs.getLong("program_id"));
                program.setProgramCode(rs.getString("program_code"));
                program.setProgramName(rs.getString("program_name"));
                return program;
            }
        };
    }

    public ResultSetHandler<Program[]> getArrayResultSetHandler() {
        return new ResultSetHandler<Program[]>() {
            public Program[] handle(ResultSet rs) throws SQLException {
                if (!rs.next()) return null;

                ArrayList<Program> programs = new ArrayList<Program>();
                do {
                    Program program = new Program();
                    program.setId(rs.getLong("program_id"));
                    program.setProgramCode(rs.getString("program_code"));
                    program.setProgramName(rs.getString("program_name"));
                    programs.add(program);
                } while (rs.next());

                return programs.toArray(new Program[programs.size()]);
            }
        };
    }

    protected String doGet(Connection connection, long id) throws SQLException {
        return "SELECT * FROM program_t WHERE program_id=?";
    }

    protected Program[] doGet(Connection connection, String[] columns, Object... params) throws SQLException {
        verifyColumns(columns, params);
        String query = String.format("SELECT * from %s WHERE %s", TABLE_NAME, buildQueryParams(columns));
        return RUN.query(connection, query, getArrayResultSetHandler(), params);
    }

    private static String buildQueryParams(String[] columns) {
        StringBuilder sb = new StringBuilder();

        for (String column : columns) {
            sb.append(column).append("=?");
        }

        return new String(sb);
    }

    protected int doPut(Connection connection, Program value) throws SQLException {
        String query = "INSERT INTO program_t(program_code, program_name) " +
                       "VALUES (?, ?)";
        return RUN.update(connection, query, value.getProgramCode(), value.getProgramName());
    }

    protected int doUpdate(Connection connection, Program newValue) throws SQLException {
        throw new UnsupportedOperationException();
    }

    protected int doDelete(Connection connection, long id) throws SQLException {
        throw new UnsupportedOperationException();
    }

    protected void verifyColumns(String[] columns, Object... params) throws SQLException {
        assert columns.length == params.length;
        ArrayList<String> invalidColumns = new ArrayList<String>();
        for (String column : columns) {
            if (!(column.equals(ID_COLUMN) ||
                  column.equals(CODE_COLUMN) ||
                  column.equals(NAME_COLUMN))) {

                invalidColumns.add(column);
            }
        }
        if (!invalidColumns.isEmpty()) {
            throw new SQLException("Some invalid columns were specified: " + Arrays.deepToString(invalidColumns.toArray()));
        }
    }
}
