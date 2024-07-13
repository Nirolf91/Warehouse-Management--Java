package main.java.com.proiectdb.warehouse.dao;

import main.java.com.proiectdb.warehouse.model.Client;
import main.java.com.proiectdb.warehouse.model.Furnizor;
import main.java.com.proiectdb.warehouse.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {

    private final Connection connection;

    public ClientDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public List<Client> getAllClienti() throws SQLException {
        List<Client> clienti = new ArrayList<>();
        String query = "SELECT * FROM Clienti";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Client client = new Client();
                client.setIdClient(resultSet.getInt("ID_CLIENT"));
                client.setNume(resultSet.getString("NUME"));
                client.setAdresa(resultSet.getString("ADRESA"));
                client.setContact(resultSet.getString("CONTACT"));

                clienti.add(client);
            }
        }
        return clienti;
    }

    public void createClient(Client client) throws SQLException {
        String query = "INSERT INTO Clienti (ID_CLIENT, NUME, ADRESA, CONTACT) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, client.getIdClient());
            preparedStatement.setString(2, client.getNume());
            preparedStatement.setString(3, client.getAdresa());
            preparedStatement.setString(4, client.getContact());

            preparedStatement.executeUpdate();
        }
    }

    public Client readClient(int idClient) throws SQLException {
        String query = "SELECT * FROM Clienti WHERE ID_CLIENT = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idClient);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Client client = new Client();
                    client.setIdClient(resultSet.getInt("ID_CLIENT"));
                    client.setNume(resultSet.getString("NUME"));
                    client.setAdresa(resultSet.getString("ADRESA"));
                    client.setContact(resultSet.getString("CONTACT"));

                    return client;
                }
            }
        }
        return null; // sau arunca o exceptie daca clientul nu este gasit
    }




    public void updateClient(Client client) throws SQLException {
        String query = "UPDATE Clienti SET NUME = ?, ADRESA = ?, CONTACT = ? WHERE ID_CLIENT = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, client.getNume());
            preparedStatement.setString(2, client.getAdresa());
            preparedStatement.setString(3, client.getContact());
            preparedStatement.setInt(4, client.getIdClient());

            preparedStatement.executeUpdate();
        }
    }

    public void deleteClient(int idClient) throws SQLException {
        String query = "DELETE FROM Clienti WHERE ID_CLIENT = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idClient);

            preparedStatement.executeUpdate();
        }
    }

    public List<String> getNumeColoane() {
        List<String> coloane = new ArrayList<>();
        String query = "SELECT * FROM Clienti WHERE 1=0"; // Query care nu returnează rânduri

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
