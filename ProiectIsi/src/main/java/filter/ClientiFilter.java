package filter;


import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientiFilter {

    public ResultSet filterClienti(String idParam, String nume, String adresa, String contact) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        StringBuilder query = new StringBuilder("SELECT * FROM Clienti WHERE 1=1");

        if (idParam != null && !idParam.isEmpty()) query.append(" AND ID_CLIENT = ?");
        if (nume != null && !nume.isEmpty()) query.append(" AND NUME LIKE ?");
        if (adresa != null && !adresa.isEmpty()) query.append(" AND ADRESA LIKE ?");
        if (contact != null && !contact.isEmpty()) query.append(" AND CONTACT LIKE ?");

        PreparedStatement pstmt = connection.prepareStatement(query.toString());
        int index = 1;

        if (idParam != null && !idParam.isEmpty()) pstmt.setInt(index++, Integer.parseInt(idParam));
        if (nume != null && !nume.isEmpty()) pstmt.setString(index++, "%" + nume + "%");
        if (adresa != null && !adresa.isEmpty()) pstmt.setString(index++, "%" + adresa + "%");
        if (contact != null && !contact.isEmpty()) pstmt.setString(index, "%" + contact + "%");

        return pstmt.executeQuery();
    }
}
