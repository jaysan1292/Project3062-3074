package com.jaysan1292.project.c3062.db;

import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * Date: 01/11/12
 * Time: 12:27 AM
 *
 * @author Jason Recillo
 */
public class DerbyHelper {
    private DerbyHelper() {}

    public static boolean tableAlreadyExists(SQLException e) {
        return "X0Y32".equals(e.getSQLState());
    }
}
