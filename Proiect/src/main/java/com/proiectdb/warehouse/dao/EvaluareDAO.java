package main.java.com.proiectdb.warehouse.dao;

import main.java.com.proiectdb.warehouse.model.Evaluare;
import main.java.com.proiectdb.warehouse.model.Furnizor;
import main.java.com.proiectdb.warehouse.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EvaluareDAO {

    private final Connection connection;
    public EvaluareDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public List<Evaluare> getAllEvaluari() throws SQLException {
        List<Evaluare> evaluari = new ArrayList<>();
        String query = "SELECT * FROM Evaluari";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                Evaluare evaluare = new Evaluare();
                evaluare.setIdEvaluare(resultSet.getInt("ID_EVALUARE"));
                evaluare.setIdClient(resultSet.getInt("ID_CLIENT"));
                evaluare.setFeedback(resultSet.getString("FEEDBACK"));
                evaluare.setScor(resultSet.getInt("SCOR"));
                evaluare.setDataEvaluarii(resultSet.getDate("DATA_EVALUARII"));

                evaluari.add(evaluare);
            }
        }
        return evaluari;
    }

    public void updateEvaluare(Evaluare evaluare) throws SQLException {
        String query = "UPDATE Evaluari SET ID_CLIENT = ?, SCOR = ?, FEEDBACK = ?, DATA_EVALUARII = ? WHERE ID_EVALUARE = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Setarea parametrilor interogării
            preparedStatement.setInt(1, evaluare.getIdClient());
            preparedStatement.setInt(2, evaluare.getScor());
            preparedStatement.setString(3, evaluare.getFeedback());
            preparedStatement.setDate(4, new java.sql.Date(evaluare.getDataEvaluarii().getTime()));
            preparedStatement.setInt(5, evaluare.getIdEvaluare());

            // Executarea interogării
            preparedStatement.executeUpdate();
        }
    }


    public void deleteEvaluare(int idEvaluare) throws SQLException {
        String query = "DELETE FROM Evaluari WHERE ID_EVALUARE = ?";
        // Codul de implementare...
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idEvaluare);

            preparedStatement.executeUpdate();
        }
    }

    public void createEvaluare(Evaluare evaluare) throws SQLException {
        String query = "INSERT INTO Evaluari (ID_EVALUARE, ID_CLIENT, SCOR, FEEDBACK, DATA_EVALUARII) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, evaluare.getIdEvaluare());
            preparedStatement.setInt(2, evaluare.getIdClient());
            preparedStatement.setInt(3, evaluare.getScor());
            preparedStatement.setString(4, evaluare.getFeedback());
            preparedStatement.setDate(5, new java.sql.Date(evaluare.getDataEvaluarii().getTime()));

            preparedStatement.executeUpdate();
        }
    }
    public Evaluare readEvaluare(int idEvaluare) throws SQLException {
        String query = "SELECT * FROM Evaluari WHERE ID_EVALUARE = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idEvaluare);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Evaluare evaluare = new Evaluare();
                    evaluare.setIdEvaluare(resultSet.getInt("ID_EVALUARE"));
                    evaluare.setIdClient(resultSet.getInt("ID_CLIENT"));
                    evaluare.setScor(resultSet.getInt("SCOR"));
                    evaluare.setFeedback(resultSet.getString("FEEDBACK"));
                    evaluare.setDataEvaluarii(resultSet.getDate("DATA_EVALUARII"));

                    return evaluare;
                }
            }
        }
        return null; // sau arunca o exceptie daca evaluarea nu este gasita
    }

    public List<String> getNumeColoane() {
        List<String> coloane = new ArrayList<>();
        String query = "SELECT * FROM Evaluari WHERE 1=0"; // Query care nu returnează rânduri

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
