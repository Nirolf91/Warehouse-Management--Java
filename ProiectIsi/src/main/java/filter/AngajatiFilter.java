package filter;

import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AngajatiFilter {

    public ResultSet filterAngajati(String idParam, String nume, String functie, String contact) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        StringBuilder query = new StringBuilder("SELECT * FROM Angajati WHERE 1=1");

        if (idParam != null && !idParam.isEmpty()) query.append(" AND ID_ANGAJAT = ?");
        if (nume != null && !nume.isEmpty()) query.append(" AND NUME LIKE ?");
        if (functie != null && !functie.isEmpty()) query.append(" AND FUNCTIE LIKE ?");
        if (contact != null && !contact.isEmpty()) query.append(" AND DATE_DE_CONTACT LIKE ?");

        PreparedStatement pstmt = connection.prepareStatement(query.toString());
        int index = 1;

        if (idParam != null && !idParam.isEmpty()) pstmt.setInt(index++, Integer.parseInt(idParam));
        if (nume != null && !nume.isEmpty()) pstmt.setString(index++, "%" + nume + "%");
        if (functie != null && !functie.isEmpty()) pstmt.setString(index++, "%" + functie + "%");
        if (contact != null && !contact.isEmpty()) pstmt.setString(index, "%" + contact + "%");

        return pstmt.executeQuery();
    }
}