package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String DB_USER = "system";
    private static final String DB_PASSWORD = "Nirolf918";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driverul JDBC Oracle nu a fost găsit.", e);
        }
    }
}
