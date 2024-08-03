
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/todo_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Pawan@9889";
    private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver"; // MySQL driver class

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName(DRIVER_CLASS); // Load the MySQL driver
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL Driver not found", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
