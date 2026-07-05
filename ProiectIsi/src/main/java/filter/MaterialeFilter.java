package filter;


import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MaterialeFilter {

    public ResultSet filterMateriale(String idParam, String nume, String descriere, String cantitate, String pret, String idFurnizor) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        StringBuilder query = new StringBuilder("SELECT * FROM Materiale WHERE 1=1");

        if (idParam != null && !idParam.isEmpty()) query.append(" AND ID_MATERIAL = ?");
        if (nume != null && !nume.isEmpty()) query.append(" AND NUME LIKE ?");
        if (descriere != null && !descriere.isEmpty()) query.append(" AND DESCRIERE LIKE ?");
        if (cantitate != null && !cantitate.isEmpty()) query.append(" AND CANTITATE_IN_STOC = ?");
        if (pret != null && !pret.isEmpty()) query.append(" AND PRET_UNITAR = ?");
        if (idFurnizor != null && !idFurnizor.isEmpty()) query.append(" AND ID_FURNIZOR = ?");

        PreparedStatement pstmt = connection.prepareStatement(query.toString());
        int index = 1;

        if (idParam != null && !idParam.isEmpty()) pstmt.setInt(index++, Integer.parseInt(idParam));
        if (nume != null && !nume.isEmpty()) pstmt.setString(index++, "%" + nume + "%");
        if (descriere != null && !descriere.isEmpty()) pstmt.setString(index++, "%" + descriere + "%");
        if (cantitate != null && !cantitate.isEmpty()) pstmt.setInt(index++, Integer.parseInt(cantitate));
        if (pret != null && !pret.isEmpty()) pstmt.setDouble(index++, Double.parseDouble(pret));
        if (idFurnizor != null && !idFurnizor.isEmpty()) pstmt.setInt(index, Integer.parseInt(idFurnizor));

        return pstmt.executeQuery();
    }
}
