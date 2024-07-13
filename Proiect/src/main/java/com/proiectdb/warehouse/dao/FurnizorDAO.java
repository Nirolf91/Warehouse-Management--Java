package main.java.com.proiectdb.warehouse.dao;

import main.java.com.proiectdb.warehouse.model.Furnizor;
import main.java.com.proiectdb.warehouse.model.Material;
import main.java.com.proiectdb.warehouse.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class FurnizorDAO {

    private final Connection connection;
    public FurnizorDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }


    // Metoda pentru a citi toti furnizorii
    public List<Furnizor> getAllFurnizori() throws SQLException {
        List<Furnizor> furnizori = new ArrayList<>();
        String query = "SELECT * FROM Furnizori";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Furnizor furnizor = new Furnizor();
                furnizor.setIdFurnizor(resultSet.getInt("ID_FURNIZOR"));
                furnizor.setNume(resultSet.getString("NUME"));
                furnizor.setAdresa(resultSet.getString("ADRESA"));
                furnizor.setContact(resultSet.getString("CONTACT"));

                furnizori.add(furnizor);
            }
        }
        return furnizori;
    }



    public void updateFurnizor(Furnizor furnizor) throws SQLException {
        String query = "UPDATE Furnizori SET NUME = ?, ADRESA = ?, CONTACT = ? WHERE ID_FURNIZOR = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, furnizor.getNume());
            preparedStatement.setString(2, furnizor.getAdresa());
            preparedStatement.setString(3, furnizor.getContact());
            preparedStatement.setInt(4, furnizor.getIdFurnizor());

            preparedStatement.executeUpdate();
        }
    }


    public void deleteFurnizor(int idFurnizor) throws SQLException {
        String query = "DELETE FROM Furnizori WHERE ID_FURNIZOR = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idFurnizor);

            preparedStatement.executeUpdate();
        }
    }

    public void createFurnizor(Furnizor furnizor) throws SQLException {
        String query = "INSERT INTO Furnizori (ID_FURNIZOR, NUME, ADRESA, CONTACT) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, furnizor.getIdFurnizor());
            preparedStatement.setString(2, furnizor.getNume());
            preparedStatement.setString(3, furnizor.getAdresa());
            preparedStatement.setString(4, furnizor.getContact());

            preparedStatement.executeUpdate();
        }
    }
    public Furnizor readFurnizor(int idFurnizor) throws SQLException {
        String query = "SELECT * FROM Furnizori WHERE ID_FURNIZOR = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idFurnizor);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Furnizor furnizor = new Furnizor();
                    furnizor.setIdFurnizor(resultSet.getInt("ID_FURNIZOR"));
                    furnizor.setNume(resultSet.getString("NUME"));
                    furnizor.setAdresa(resultSet.getString("ADRESA"));
                    furnizor.setContact(resultSet.getString("CONTACT"));

                    return furnizor;
                }
            }
        }
        return null; // sau arunca o exceptie daca furnizorul nu este gasit
    }

    public List<String> getNumeColoane() {
        List<String> coloane = new ArrayList<>();
        String query = "SELECT * FROM Furnizori WHERE 1=0"; // Query care nu returnează rânduri

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
