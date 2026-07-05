package filter;

import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EvaluariFilter {

    public ResultSet filterEvaluari(String idEvaluare, String idClient, String scor, String feedback, String dataEvaluare) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        StringBuilder query = new StringBuilder("SELECT * FROM Evaluari WHERE 1=1");

        if (idEvaluare != null && !idEvaluare.isEmpty()) query.append(" AND ID_EVALUARE = ?");
        if (idClient != null && !idClient.isEmpty()) query.append(" AND ID_CLIENT = ?");
        if (scor != null && !scor.isEmpty()) query.append(" AND SCOR = ?");
        if (feedback != null && !feedback.isEmpty()) query.append(" AND FEEDBACK LIKE ?");
        if (dataEvaluare != null && !dataEvaluare.isEmpty()) query.append(" AND DATA_EVALUARII = ?");

        PreparedStatement pstmt = connection.prepareStatement(query.toString());
        int index = 1;

        if (idEvaluare != null && !idEvaluare.isEmpty()) pstmt.setInt(index++, Integer.parseInt(idEvaluare));
        if (idClient != null && !idClient.isEmpty()) pstmt.setInt(index++, Integer.parseInt(idClient));
        if (scor != null && !scor.isEmpty()) pstmt.setInt(index++, Integer.parseInt(scor));
        if (feedback != null && !feedback.isEmpty()) pstmt.setString(index++, "%" + feedback + "%");
        if (dataEvaluare != null && !dataEvaluare.isEmpty()) pstmt.setDate(index, java.sql.Date.valueOf(dataEvaluare));

        return pstmt.executeQuery();
    }
}
