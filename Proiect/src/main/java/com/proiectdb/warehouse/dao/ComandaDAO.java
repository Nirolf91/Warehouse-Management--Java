package main.java.com.proiectdb.warehouse.dao;

import main.java.com.proiectdb.warehouse.model.Comanda;
import main.java.com.proiectdb.warehouse.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComandaDAO {

    private final Connection connection;
    public ComandaDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public List<Comanda> getAllComenzi() throws SQLException {
        List<Comanda> comenzi = new ArrayList<>();
        String query = "SELECT * FROM Comenzi";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Comanda comanda = new Comanda();
                comanda.setIdComanda(resultSet.getInt("ID_COMANDA"));
                comanda.setCantitate(resultSet.getInt("CANTITATE"));
                comanda.setTotalComanda(resultSet.getInt("TOTAL_COMANDA"));
                comanda.setStatutComanda(resultSet.getString("STATUT_COMANDA"));
                comanda.setTipComanda(resultSet.getString("TIP_COMANDA"));
                comanda.setIdFurnizor(resultSet.getInt("ID_FURNIZOR"));
                comanda.setIdMaterial(resultSet.getInt("ID_MATERIAL"));
                comanda.setIdClient(resultSet.getInt("ID_CLIENT"));
                comanda.setDataComenzii(resultSet.getDate("DATA_COMENZII"));
                comanda.setPretTotal(resultSet.getInt("PRET_TOTAL"));
                comanda.setIdAngajat(resultSet.getInt("ID_ANGAJAT"));

                comenzi.add(comanda);
            }
        }
        return comenzi;
    }

    public void createComanda(Comanda comanda) throws SQLException {
        String query = "INSERT INTO Comenzi (ID_COMANDA, DATA_COMENZII, ID_CLIENT, ID_FURNIZOR, ID_ANGAJAT, ID_MATERIAL, TOTAL_COMANDA, STATUT_COMANDA, TIP_COMANDA, CANTITATE, PRET_TOTAL) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, comanda.getIdComanda());
            preparedStatement.setDate(2, new java.sql.Date(comanda.getDataComenzii().getTime()));
            preparedStatement.setInt(3, comanda.getIdClient());
            preparedStatement.setInt(4, comanda.getIdFurnizor());
            preparedStatement.setInt(5, comanda.getIdAngajat());
            preparedStatement.setInt(6, comanda.getIdMaterial());
            preparedStatement.setDouble(7, comanda.getTotalComanda());
            preparedStatement.setString(8, comanda.getStatutComanda());
            preparedStatement.setString(9, comanda.getTipComanda());
            preparedStatement.setInt(10, comanda.getCantitate());
            preparedStatement.setDouble(11, comanda.getPretTotal());

            preparedStatement.executeUpdate();
        }
    }

    public Comanda readComanda(int idComanda) throws SQLException {
        String query = "SELECT * FROM Comenzi WHERE ID_COMANDA = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idComanda);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Comanda comanda = new Comanda();
                    comanda.setIdComanda(resultSet.getInt("ID_COMANDA"));
                    comanda.setDataComenzii(resultSet.getDate("DATA_COMENZII"));
                    comanda.setIdClient(resultSet.getInt("ID_CLIENT"));
                    comanda.setIdFurnizor(resultSet.getInt("ID_FURNIZOR"));
                    comanda.setIdAngajat(resultSet.getInt("ID_ANGAJAT"));
                    comanda.setIdMaterial(resultSet.getInt("ID_MATERIAL"));
                    comanda.setTotalComanda(resultSet.getDouble("TOTAL_COMANDA"));
                    comanda.setStatutComanda(resultSet.getString("STATUT_COMANDA"));
                    comanda.setTipComanda(resultSet.getString("TIP_COMANDA"));
                    comanda.setCantitate(resultSet.getInt("CANTITATE"));
                    comanda.setPretTotal(resultSet.getDouble("PRET_TOTAL"));

                    return comanda;
                }
            }
        }
        return null; // sau arunca o exceptie daca comanda nu este gasita
    }

    public void updateComanda(Comanda comanda) throws SQLException {
        String query = "UPDATE Comenzi SET DATA_COMENZII = ?, ID_CLIENT = ?, ID_FURNIZOR = ?, ID_ANGAJAT = ?, ID_MATERIAL = ?, TOTAL_COMANDA = ?, STATUT_COMANDA = ?, TIP_COMANDA = ?, CANTITATE = ?, PRET_TOTAL = ? WHERE ID_COMANDA = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDate(1, new java.sql.Date(comanda.getDataComenzii().getTime()));
            preparedStatement.setInt(2, comanda.getIdClient());
            preparedStatement.setInt(3, comanda.getIdFurnizor());
            preparedStatement.setInt(4, comanda.getIdAngajat());
            preparedStatement.setInt(5, comanda.getIdMaterial());
            preparedStatement.setDouble(6, comanda.getTotalComanda());
            preparedStatement.setString(7, comanda.getStatutComanda());
            preparedStatement.setString(8, comanda.getTipComanda());
            preparedStatement.setInt(9, comanda.getCantitate());
            preparedStatement.setDouble(10, comanda.getPretTotal());
            preparedStatement.setInt(11, comanda.getIdComanda());

            preparedStatement.executeUpdate();
        }
    }

    public void deleteComanda(int idComanda) throws SQLException {
        String query = "DELETE FROM Comenzi WHERE ID_COMANDA = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idComanda);

            preparedStatement.executeUpdate();
        }
    }

    public List<String> getNumeColoane() {
        List<String> coloane = new ArrayList<>();
        String query = "SELECT * FROM Comenzi WHERE 1=0"; // Query care nu returnează rânduri

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            ResultSetMetaData metaData = resultSet.getMetaData();
            int numarColoane = metaData.getColumnCount();

            for (int i = 1; i <= numarColoane; i++) {
                coloane.add(metaData.getColumnName(i));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Tratează sau propagă excepția după caz
        }

        return coloane;
    }
}
