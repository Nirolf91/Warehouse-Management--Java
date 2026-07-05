package filter;


import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransportatoriFilter {

    public ResultSet filterTransportatori(String idTransportator, String nume, String contact, String pretPeKg) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        StringBuilder query = new StringBuilder("SELECT * FROM Transportatori WHERE 1=1");

        // Adds filter conditions only when parameters are valid
        if (idTransportator != null && !idTransportator.isEmpty()) query.append(" AND ID_TRANSPORTATOR = ?");
        if (nume != null && !nume.isEmpty()) query.append(" AND NUME LIKE ?");
        if (contact != null && !contact.isEmpty()) query.append(" AND CONTACT LIKE ?");
        if (pretPeKg != null && !pretPeKg.isEmpty()) query.append(" AND PRET_PE_KG = ?");

        PreparedStatement pstmt = connection.prepareStatement(query.toString());
        int index = 1;

        // Binds query parameters
        if (idTransportator != null && !idTransportator.isEmpty()) pstmt.setInt(index++, Integer.parseInt(idTransportator));
        if (nume != null && !nume.isEmpty()) pstmt.setString(index++, "%" + nume + "%");
        if (contact != null && !contact.isEmpty()) pstmt.setString(index++, "%" + contact + "%");
        if (pretPeKg != null && !pretPeKg.isEmpty()) pstmt.setDouble(index, Double.parseDouble(pretPeKg));

        return pstmt.executeQuery();
    }
}
