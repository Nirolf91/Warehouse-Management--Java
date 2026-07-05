package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DEFAULT_DB_URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String DEFAULT_DB_USER = "system";
    private static final String DEFAULT_DB_PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            return DriverManager.getConnection(
                    getConfigValue("WAREHOUSE_DB_URL", "warehouse.db.url", DEFAULT_DB_URL),
                    getConfigValue("WAREHOUSE_DB_USER", "warehouse.db.user", DEFAULT_DB_USER),
                    getConfigValue("WAREHOUSE_DB_PASSWORD", "warehouse.db.password", DEFAULT_DB_PASSWORD)
            );
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driverul JDBC Oracle nu a fost găsit.", e);
        }
    }

    private static String getConfigValue(String environmentKey, String systemPropertyKey, String defaultValue) {
        String systemPropertyValue = System.getProperty(systemPropertyKey);
        if (systemPropertyValue != null && !systemPropertyValue.trim().isEmpty()) {
            return systemPropertyValue;
        }

        String environmentValue = System.getenv(environmentKey);
        if (environmentValue != null && !environmentValue.trim().isEmpty()) {
            return environmentValue;
        }

        return defaultValue;
    }
}
