package main.java.com.proiectdb.warehouse.dao;

import main.java.com.proiectdb.warehouse.model.Transportator;
import main.java.com.proiectdb.warehouse.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransportatorDAO {

    private final Connection connection;

    public TransportatorDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public List<Transportator> getAllTransportatori() throws SQLException {
        List<Transportator> transportatori = new ArrayList<>();
        String query = "SELECT * FROM Transportatori";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Transportator transportator = new Transportator();
                transportator.setIdTransportator(resultSet.getInt("ID_TRANSPORTATOR"));
                transportator.setNume(resultSet.getString("NUME"));
                transportator.setContact(resultSet.getString("CONTACT"));
                transportator.setPretPeKg(resultSet.getInt("PRET_PE_KG"));

                transportatori.add(transportator);
            }
        }
        return transportatori;
    }

    public void updateTransportator(Transportator transportator) throws SQLException {
        String query = "UPDATE Transportatori SET NUME = ?, CONTACT = ?, PRET_PE_KG = ? WHERE ID_TRANSPORTATOR = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, transportator.getNume());
            preparedStatement.setString(2, transportator.getContact());
            preparedStatement.setDouble(3, transportator.getPretPeKg());
            preparedStatement.setInt(4, transportator.getIdTransportator());

            preparedStatement.executeUpdate();
        }
    }


    public void deleteTransportator(int idTransportator) throws SQLException {
        String query = "DELETE FROM Transportatori WHERE ID_TRANSPORTATOR = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idTransportator);

            preparedStatement.executeUpdate();
        }
    }

    public void createTransportator(Transportator transportator) throws SQLException {
        String query = "INSERT INTO Transportatori (NUME, CONTACT, PRET_PE_KG) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, transportator.getNume());
            preparedStatement.setString(2, transportator.getContact());
            preparedStatement.setDouble(3, transportator.getPretPeKg());

            preparedStatement.executeUpdate();
        }
    }
    public Transportator readTransportator(int idTransportator) throws SQLException {
        String query = "SELECT * FROM Transportatori WHERE ID_TRANSPORTATOR = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idTransportator);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Transportator transportator = new Transportator();
                    transportator.setIdTransportator(resultSet.getInt("ID_TRANSPORTATOR"));
                    transportator.setNume(resultSet.getString("NUME"));
                    transportator.setContact(resultSet.getString("CONTACT"));
                    transportator.setPretPeKg(resultSet.getDouble("PRET_PE_KG"));

                    return transportator;
                }
            }
        }
        return null; // sau arunca o exceptie daca transportatorul nu este gasit
    }

    public List<String> getNumeColoane() {
        List<String> coloane = new ArrayList<>();
        String query = "SELECT * FROM Transportatori WHERE 1=0"; // Query care nu returnează rânduri

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
