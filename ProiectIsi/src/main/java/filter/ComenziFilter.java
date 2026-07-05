package filter;

import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ComenziFilter {

    /**
     * Filtrează comenzile pe baza parametrilor dați.
     *
     * @param idComanda       ID-ul comenzii
     * @param dataComenzii    Data comenzii
     * @param idClient        ID-ul clientului
     * @param idFurnizor      ID-ul furnizorului
     * @param idAngajat       ID-ul angajatului
     * @param idMaterial      ID-ul materialului
     * @param totalComanda    Totalul comenzii
     * @param statutComanda   Statutul comenzii
     * @param tipComanda      Tipul comenzii
     * @param cantitate       Cantitatea comenzii
     * @param pretTotal       Prețul total
     * @param idTransportator ID-ul transportatorului
     * @return ResultSet cu rezultatele filtrării
     * @throws SQLException dacă apare o eroare SQL
     */
    public ResultSet filterComenzi(String idComanda, String dataComenzii, String idClient, String idFurnizor,
                                   String idAngajat, String idMaterial, String totalComanda, String statutComanda,
                                   String tipComanda, String cantitate, String pretTotal, String idTransportator)
            throws SQLException {

        Connection connection = DatabaseConnection.getConnection();
        StringBuilder query = new StringBuilder("SELECT * FROM Comenzi WHERE 1=1");

        // Construirea query-ului cu parametrii validați
        if (idComanda != null && !idComanda.trim().isEmpty()) query.append(" AND ID_COMANDA = ?");
        if (dataComenzii != null && !dataComenzii.trim().isEmpty()) query.append(" AND DATA_COMENZII = ?");
        if (idClient != null && !idClient.trim().isEmpty()) query.append(" AND ID_CLIENT = ?");
        if (idFurnizor != null && !idFurnizor.trim().isEmpty()) query.append(" AND ID_FURNIZOR = ?");
        if (idAngajat != null && !idAngajat.trim().isEmpty()) query.append(" AND ID_ANGAJAT = ?");
        if (idMaterial != null && !idMaterial.trim().isEmpty()) query.append(" AND ID_MATERIAL = ?");
        if (totalComanda != null && !totalComanda.trim().isEmpty()) query.append(" AND TOTAL_COMANDA = ?");
        if (statutComanda != null && !statutComanda.trim().isEmpty()) query.append(" AND STATUT_COMANDA LIKE ?");
        if (tipComanda != null && !tipComanda.trim().isEmpty()) query.append(" AND TIP_COMANDA LIKE ?");
        if (cantitate != null && !cantitate.trim().isEmpty()) query.append(" AND CANTITATE = ?");
        if (pretTotal != null && !pretTotal.trim().isEmpty()) query.append(" AND PRET_TOTAL = ?");
        if (idTransportator != null && !idTransportator.trim().isEmpty()) query.append(" AND ID_TRANSPORTATOR = ?");

        // Pregătirea query-ului
        PreparedStatement pstmt = connection.prepareStatement(query.toString());
        int index = 1;

        try {
            // Popularea parametrilor query-ului
            if (idComanda != null && !idComanda.trim().isEmpty()) pstmt.setInt(index++, Integer.parseInt(idComanda));
            if (dataComenzii != null && !dataComenzii.trim().isEmpty()) pstmt.setDate(index++, java.sql.Date.valueOf(dataComenzii));
            if (idClient != null && !idClient.trim().isEmpty()) pstmt.setInt(index++, Integer.parseInt(idClient));
            if (idFurnizor != null && !idFurnizor.trim().isEmpty()) pstmt.setInt(index++, Integer.parseInt(idFurnizor));
            if (idAngajat != null && !idAngajat.trim().isEmpty()) pstmt.setInt(index++, Integer.parseInt(idAngajat));
            if (idMaterial != null && !idMaterial.trim().isEmpty()) pstmt.setInt(index++, Integer.parseInt(idMaterial));
            if (totalComanda != null && !totalComanda.trim().isEmpty()) pstmt.setDouble(index++, Double.parseDouble(totalComanda));
            if (statutComanda != null && !statutComanda.trim().isEmpty()) pstmt.setString(index++, "%" + statutComanda + "%");
            if (tipComanda != null && !tipComanda.trim().isEmpty()) pstmt.setString(index++, "%" + tipComanda + "%");
            if (cantitate != null && !cantitate.trim().isEmpty()) pstmt.setInt(index++, Integer.parseInt(cantitate));
            if (pretTotal != null && !pretTotal.trim().isEmpty()) pstmt.setDouble(index++, Double.parseDouble(pretTotal));
            if (idTransportator != null && !idTransportator.trim().isEmpty()) pstmt.setInt(index++, Integer.parseInt(idTransportator));
        } catch (NumberFormatException e) {
            System.err.println("Eroare de conversie a parametrilor: " + e.getMessage());
            throw new SQLException("Parametru invalid: " + e.getMessage());
        }

        // Logare pentru debugging
        System.out.println("Query generat: " + query);
        System.out.println("Parametri setați pentru query.");

        // Execută query-ul și returnează rezultatul
        return pstmt.executeQuery();
    }
}
