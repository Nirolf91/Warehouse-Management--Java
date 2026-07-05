package service;

import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LogService {
    public static void logAction(String utilizator, String rol, String actiuneLogica, String comandaSQL) {
        String insertLogQuery = "INSERT INTO Logs (UTILIZATOR, ROL, ACTIUNE_LOGICA, COMANDA_SQL) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(insertLogQuery)) {
            pstmt.setString(1, utilizator);
            pstmt.setString(2, rol);
            pstmt.setString(3, actiuneLogica);
            pstmt.setString(4, comandaSQL);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Pentru debugging, se poate înlocui cu un sistem de logare extern
        }
    }
}
