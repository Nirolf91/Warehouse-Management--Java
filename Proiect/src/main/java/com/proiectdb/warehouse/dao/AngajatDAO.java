package main.java.com.proiectdb.warehouse.dao;

import main.java.com.proiectdb.warehouse.model.Angajat;
import main.java.com.proiectdb.warehouse.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AngajatDAO {
    private final Connection connection;
    public AngajatDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public List<Angajat> getAllAngajati() throws SQLException {
        List<Angajat> angajati = new ArrayList<>();
        String query = "SELECT * FROM Angajati";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Angajat angajat = new Angajat();
                angajat.setIdAngajat(resultSet.getInt("ID_ANGAJAT"));
                angajat.setNume(resultSet.getString("NUME"));
                angajat.setFunctie(resultSet.getString("FUNCTIE"));
                angajat.setDateDeContact(resultSet.getString("DATE_DE_CONTACT"));

                angajati.add(angajat);
            }
        }
        return angajati;
    }

    public void createAngajat(Angajat angajat) throws SQLException {
        String query = "INSERT INTO Angajati (ID_ANGAJAT, NUME, FUNCTIE, DATE_DE_CONTACT) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, angajat.getIdAngajat());
            preparedStatement.setString(2, angajat.getNume());
            preparedStatement.setString(3, angajat.getFunctie());
            preparedStatement.setString(4, angajat.getDateDeContact());

            preparedStatement.executeUpdate();
        }
    }
    public void updateAngajat(Angajat angajat) throws SQLException {
        String query = "UPDATE Angajati SET NUME = ?, FUNCTIE = ?, DATE_DE_CONTACT = ? WHERE ID_ANGAJAT = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, angajat.getNume());
            preparedStatement.setString(2, angajat.getFunctie());
            preparedStatement.setString(3, angajat.getDateDeContact());
            preparedStatement.setInt(4, angajat.getIdAngajat());
            preparedStatement.executeUpdate();
        }
    }
    public void deleteAngajat(int idAngajat) throws SQLException {
        String query = "DELETE FROM Angajati WHERE ID_ANGAJAT = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idAngajat);

            preparedStatement.executeUpdate();

        
        }
    }

    public Angajat readAngajat(int idAngajat) throws SQLException {
        String query = "SELECT * FROM Angajati WHERE ID_ANGAJAT = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idAngajat);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Angajat angajat = new Angajat();
                    angajat.setIdAngajat(resultSet.getInt("ID_ANGAJAT"));
                    angajat.setNume(resultSet.getString("NUME"));
                    angajat.setFunctie(resultSet.getString("FUNCTIE"));
                    angajat.setDateDeContact(resultSet.getString("DATE_DE_CONTACT"));

                    return angajat;
                }
            }
        }
        return null; // sau arunca o exceptie daca angajatul nu este gasit
    }

    public List<String> getNumeColoane() {
        List<String> coloane = new ArrayList<>();
        String query = "SELECT * FROM Angajati WHERE 1=0"; // Query care nu returnează rânduri

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






